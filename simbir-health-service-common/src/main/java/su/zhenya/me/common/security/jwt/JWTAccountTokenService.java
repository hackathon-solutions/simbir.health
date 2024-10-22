package su.zhenya.me.common.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.AccountCredentials;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.AccountToken;
import su.zhenya.me.common.security.core.access.AccountTokenDescriptor;
import su.zhenya.me.common.security.core.access.AccountTokenService;
import su.zhenya.me.common.security.core.provider.account.AccountNotFoundException;
import su.zhenya.me.common.security.core.provider.account.AccountProvider;

// TODO: возможно, все таки стоит добавить полную ("интроспекцию" - далее token_info) куда-нибудь к verify, однако нужно ли это
// в рамках текущих сервисов? token_info по verify и так является token_info. судя по всему спека oauth
// устарела еще до того, как появился JWT. но - спека не ограничивает нас в реализации token_info, она лишь обязывает
// передавать свойство дееспособности токена, так называемое поле active. ref на спеку:
/*
 * The Token Introspection Endpoint should respond with a JSON object with the properties listed below.
 * Only the “active” property is required, the rest are optional. Some of the properties in the Introspection spec
 * are specifically for JWT tokens, so we will only cover the basic ones here. You can also add additional properties
 * in the response if you have additional information about a token that may be useful.
 */
// scopes, client_id, конечно, тема, но реализовывать свой ID дорого стоит(, да и в рамках текущей задачи не требуется.
// но на будущее можно глянуть да
@RequiredArgsConstructor
public class JWTAccountTokenService implements AccountTokenService {

    private static final String DEFAULT_ISSUER_NAME = "me.zhenya.su ISSUER";
    private static final long DEFAULT_ACCESS_TOKEN_DURATION_MS = 1800000;

    private static final String REFRESH_ISSUER_NAME = DEFAULT_ISSUER_NAME + " refresh";

    private final AccountProvider accountProvider;
    private final ServiceSecret serviceSecret;

    @Override
    public AccountToken releaseAccountToken(AccountCredentials credentials) {
        Account account = accountProvider.findByUsername(credentials.getUsername())
                                         .filter(acc -> acc.getCredentials().equals(credentials))
                                         .orElseThrow(AccountNotFoundException::new);

        String accessToken = createAccessTokenFrom(account);

        return new AccountToken(account.getAccountId(), accessToken);
    }

    @Override
    public AccountToken releaseAccountToken(CharSequence refreshToken) {
        if (!verifyRefreshToken(refreshToken)) {
            throw new JWTVerificationException("the refresh token is not valid");
        }

        AccountTokenDescriptor descriptor = getAccountTokenDescriptor(refreshToken);
        Account account = accountProvider.findByUsername(descriptor.getUsername()).orElseThrow(AccountNotFoundException::new);

        String accessToken = createAccessTokenFrom(account);

        return new AccountToken(account.getAccountId(), accessToken);
    }

    @Override
    public CharSequence releaseRefreshToken(AccountCredentials credentials) {
        Account account = accountProvider.findByUsername(credentials.getUsername())
                                         .filter(acc -> acc.getCredentials().equals(credentials))
                                         .orElseThrow(AccountNotFoundException::new);

        return createDefaultJWTBuilderFrom(AccountTokenDescriptor.of(account))
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(
                        LocalDateTime.now().plus(DEFAULT_ACCESS_TOKEN_DURATION_MS, ChronoUnit.MILLIS).atZone(ZoneId.systemDefault()).toInstant()
                ))
                .withIssuer(REFRESH_ISSUER_NAME)
                .withClaim("refresh", true)
                .sign(Algorithm.HMAC256(serviceSecret.getSecret()));
    }

    private static JWTCreator.Builder createDefaultJWTBuilderFrom(AccountTokenDescriptor descriptor) {
        JWTCreator.Builder builder = JWT.create();
        Arrays.stream(descriptor.getClass().getDeclaredFields())
              .forEach(field -> {
                  try {
                      field.setAccessible(true);
                      builder.withClaim(field.getName(), field.get(descriptor).toString());
                  } catch (IllegalAccessException e) {
                      throw new RuntimeException(e);
                  }
              });
        return builder;
    }

    @Override
    public boolean verifyAccountToken(CharSequence accessToken) {
        try {
            verifyJWT(accessToken);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    @Override
    public boolean verifyRefreshToken(CharSequence refreshToken) {
        try {
            JWT.require(Algorithm.HMAC256(serviceSecret.getSecret()))
               .withIssuer(REFRESH_ISSUER_NAME)
               .withClaim("refresh", true)
               .acceptExpiresAt(TimeUnit.DAYS.toSeconds(7))
               .build()
               .verify(refreshToken.toString());
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private DecodedJWT verifyJWT(CharSequence jwt) throws JWTVerificationException {
        Verification verifier = JWT.require(Algorithm.HMAC256(serviceSecret.getSecret()))
                                   .withIssuer(DEFAULT_ISSUER_NAME)
                                   .acceptExpiresAt(TimeUnit.MILLISECONDS.toSeconds(DEFAULT_ACCESS_TOKEN_DURATION_MS));

        Arrays.stream(AccountTokenDescriptor.class.getDeclaredFields())
              .forEach(field -> verifier.withClaimPresence(field.getName()));

        return verifier.build().verify(jwt.toString());
    }

    @Override
    public void suspendAccountToken(AccountToken accountToken) {
        // TODO: сделать
    }

    @Override
    public AccountToken getAccountToken(CharSequence accessToken) {
        AccountTokenDescriptor descriptor = createAccountTokenDescriptorFrom(JWT.decode(accessToken.toString()));
        return new AccountToken(descriptor.getAccountId(), accessToken);
    }

    @Override
    public AccountTokenDescriptor getAccountTokenDescriptor(CharSequence accessToken) {
        return createAccountTokenDescriptorFrom(JWT.decode(accessToken.toString()));
    }

    private String createAccessTokenFrom(Account account) {
        return createDefaultJWTBuilderFrom(AccountTokenDescriptor.of(account))
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(
                        LocalDateTime.now().plus(DEFAULT_ACCESS_TOKEN_DURATION_MS, ChronoUnit.MILLIS).atZone(ZoneId.systemDefault()).toInstant()
                ))
                .withIssuer(DEFAULT_ISSUER_NAME)
                .sign(Algorithm.HMAC256(serviceSecret.getSecret()));
    }

    private static AccountTokenDescriptor createAccountTokenDescriptorFrom(DecodedJWT decodedJWT) {
        AccountTokenDescriptor descriptor = new AccountTokenDescriptor();
        Arrays.stream(descriptor.getClass().getDeclaredFields())
              .forEach(field -> {
                  try {
                      field.setAccessible(true);
                      field.set(descriptor, decodedJWT.getClaim(field.getName()).as(field.getType()));
                  } catch (IllegalAccessException e) {
                      throw new RuntimeException(e);
                  }
              });
        return descriptor;
    }
}

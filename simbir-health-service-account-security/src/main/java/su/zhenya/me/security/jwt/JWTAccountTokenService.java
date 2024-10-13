package su.zhenya.me.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.AccountCredentials;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.AccountToken;
import su.zhenya.me.security.core.access.AccountTokenDescriptor;
import su.zhenya.me.security.core.access.AccountTokenService;
import su.zhenya.me.security.core.provider.account.AccountNotFoundException;
import su.zhenya.me.security.core.provider.account.AccountProvider;

@RequiredArgsConstructor
public class JWTAccountTokenService implements AccountTokenService {

    private static final String ISSUER = "me.zhenya.su SERVICE";

    private final AccountProvider accountProvider;
    private final ServiceSecret serviceSecret;

    @Override
    public AccountToken releaseAccountToken(AccountId accountId, AccountCredentials credentials) {
        Account account = accountProvider.findByAccountId(accountId)
                                         .filter(acc -> acc.getCredentials().equals(credentials))
                                         .orElseThrow(AccountNotFoundException::new);

        String accessToken = createDefaultJWTBuilderFrom(AccountTokenDescriptor.of(account))
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(LocalDateTime.now().plusWeeks(1).atZone(ZoneId.systemDefault()).toInstant()))
                .withIssuer(ISSUER)
                .sign(Algorithm.HMAC256(serviceSecret.getSecret()));

        return new AccountToken(accountId, accessToken);
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

    private DecodedJWT verifyJWT(CharSequence jwt) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(serviceSecret.getSecret()))
                                  .withIssuer(ISSUER)
                                  .build();
        return verifier.verify(jwt.toString());
    }

    @Override
    public void suspendAccountToken(AccountToken accountToken) {
        // TODO: сделать
    }

    @Override
    public AccountToken getAccountToken(CharSequence accessToken) {
        AccountTokenDescriptor descriptor = createAccountTokenDescriptorFrom(verifyJWT(accessToken));
        return new AccountToken(descriptor.getAccountId(), accessToken);
    }

    @Override
    public AccountTokenDescriptor getAccountTokenDescriptor(CharSequence accessToken) {
        return createAccountTokenDescriptorFrom(verifyJWT(accessToken));
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

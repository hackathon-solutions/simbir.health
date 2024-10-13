package su.zhenya.me.security.jwt;

import static su.zhenya.me.account.model.Account.listRolesToStringRoles;
import static su.zhenya.me.account.model.Account.stringRolesToListRoles;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.AccountCredentials;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.AccountToken;
import su.zhenya.me.account.model.Role;
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
                .withIssuedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .withExpiresAt(LocalDateTime.now().plusWeeks(1).toInstant(ZoneOffset.UTC))
                .withIssuer(ISSUER)
                .sign(Algorithm.HMAC256(serviceSecret.getSecret()));

        return new AccountToken(accountId, accessToken);
    }

    private static JWTCreator.Builder createDefaultJWTBuilderFrom(AccountTokenDescriptor descriptor) {
        JWTCreator.Builder builder = JWT.create();
        Arrays.stream(descriptor.getClass().getDeclaredFields())
              .forEach(field -> {
                  try {
                      builder.withClaim(field.getName(), field.get(descriptor).toString());
                  } catch (IllegalAccessException e) {
                      throw new RuntimeException(e);
                  }
              });
        return builder;
    }

    @Override
    public boolean verifyAccountToken(AccountToken accountToken) {
        try {
            verifyJWT(accountToken.getTokenValue().toString());
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private DecodedJWT verifyJWT(String jwt) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(serviceSecret.getSecret()))
                                  .withIssuer(ISSUER)
                                  .build();
        return verifier.verify(jwt);
    }

    @Override
    public void suspendAccountToken(AccountToken accountToken) {
        // TODO: сделать
    }

    private static AccountTokenDescriptor createDefaultJWTBuilderFrom(DecodedJWT decodedJWT) {
        AccountTokenDescriptor descriptor = new AccountTokenDescriptor();
        Arrays.stream(descriptor.getClass().getDeclaredFields())
              .forEach(field -> {
                  try {
                      field.setAccessible(true);
                      field.set(descriptor, decodedJWT.getClaim(field.getName()));
                  } catch (IllegalAccessException e) {
                      throw new RuntimeException(e);
                  }
              });
        return descriptor;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountTokenDescriptor {

        private long accountId;
        private String accountRoles;

        public static AccountTokenDescriptor of(Account account) {
            return new AccountTokenDescriptor(account.getAccountId().getId(), listRolesToStringRoles(account.getRoles()));
        }

        public AccountId getAccountId() {
            return new AccountId(accountId);
        }

        public List<Role> getAccountRoles() {
            return stringRolesToListRoles(accountRoles);
        }
    }
}

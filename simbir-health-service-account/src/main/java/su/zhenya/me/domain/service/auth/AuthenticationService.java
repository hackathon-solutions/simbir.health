package su.zhenya.me.domain.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.AccountCredentials;
import su.zhenya.me.account.model.AccountToken;
import su.zhenya.me.common.security.core.access.AccountTokenService;
import su.zhenya.me.domain.service.account.AccountService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountTokenService accountTokenService;
    private final AccountService accountService;

    public Account accountCreate(Account account) {
        return accountService.createAccount(account);
    }

    public AccountToken releaseToken(AccountCredentials credentials) {
        return accountTokenService.releaseAccountToken(credentials);
    }

    public CharSequence releaseRefreshToken(AccountCredentials credentials) {
        return accountTokenService.releaseRefreshToken(credentials);
    }

    public boolean verifyToken(String accessToken) {
        return accountTokenService.verifyAccountToken(accessToken);
    }

    public AccountToken refreshToken(String refreshToken) {
        return accountTokenService.releaseAccountToken(refreshToken);
    }

    public void suspendToken(AccountToken accountToken) {
        accountTokenService.suspendAccountToken(accountToken);
    }
}

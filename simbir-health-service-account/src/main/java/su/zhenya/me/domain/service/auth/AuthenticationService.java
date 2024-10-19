package su.zhenya.me.domain.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.AccountCredentials;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.AccountToken;
import su.zhenya.me.common.security.core.access.AccountTokenService;
import su.zhenya.me.domain.service.account.AccountService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountTokenService accountTokenService;
    private final AccountService accountService;

    public Account accountCreate(Account account) {
        return accountService.saveAccount(account);
    }

    public AccountToken releaseToken(AccountId accountId, AccountCredentials credentials) {
        return accountTokenService.releaseAccountToken(accountId, credentials);
    }

    public boolean verifyToken(String accessToken) {
        return accountTokenService.verifyAccountToken(accessToken);
    }

    public void suspendToken(AccountToken accountToken) {
        accountTokenService.suspendAccountToken(accountToken);
    }
}

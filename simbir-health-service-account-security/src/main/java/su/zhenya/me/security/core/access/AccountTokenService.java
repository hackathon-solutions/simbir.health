package su.zhenya.me.security.core.access;

import su.zhenya.me.account.model.AccountCredentials;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.AccountToken;

public interface AccountTokenService {

    AccountToken releaseAccountToken(AccountId accountId, AccountCredentials credentials);
    boolean verifyAccountToken(AccountToken accountToken);
    void suspendAccountToken(AccountToken accountToken);
}

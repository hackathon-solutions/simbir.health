package su.zhenya.me.security.core.provider.account;

import java.util.Optional;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.AccountId;

public interface AccountProvider {

    Optional<Account> findByAccountId(AccountId accountId);
}

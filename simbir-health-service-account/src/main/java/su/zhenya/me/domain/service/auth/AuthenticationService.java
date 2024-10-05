package su.zhenya.me.domain.service.auth;

import org.springframework.stereotype.Service;
import su.zhenya.me.account.model.AccountCredentials;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.AccountToken;

@Service
public class AuthenticationService {

    public AccountToken releaseToken(AccountId accountId, AccountCredentials credentials) {
        return null;
    }
}

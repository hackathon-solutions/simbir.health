package su.zhenya.me.api.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.zhenya.me.account.model.AccountCredentials;
import su.zhenya.me.account.model.AccountId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseAccountTokenRequest {

    private AccountId accountId;
    private AccountCredentials credentials;
}

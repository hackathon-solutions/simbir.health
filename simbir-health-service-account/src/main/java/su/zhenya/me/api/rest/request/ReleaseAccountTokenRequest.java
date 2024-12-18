package su.zhenya.me.api.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.zhenya.me.account.model.AccountCredentials;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseAccountTokenRequest {

    private AccountCredentials credentials;
}

package su.zhenya.me.api.rest.request;

import lombok.Builder;
import lombok.Data;
import su.zhenya.me.account.model.AccountCredentials;

@Data
@Builder
public class AccountCreateRequest {

    private String firstName;
    private String lastName;
    private AccountCredentials credentials;
}

package su.zhenya.me.api.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.zhenya.me.account.model.AccountCredentials;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountSignUpRequest {

    private String firstName;
    private String lastName;
    private AccountCredentials credentials;
}

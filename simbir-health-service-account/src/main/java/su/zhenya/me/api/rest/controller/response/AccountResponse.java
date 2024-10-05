package su.zhenya.me.api.rest.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.Role;
import su.zhenya.me.api.rest.controller.dto.AccountCredentialsDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private AccountId accountId;
    private String firstName;
    private String lastName;
    private AccountCredentialsDto credentials;
    private Role accountRole;
}

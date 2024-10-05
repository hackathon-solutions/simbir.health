package su.zhenya.me.api.rest.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.zhenya.me.account.model.AccountCredentials;
import su.zhenya.me.account.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateRequest {

    private String firstName;
    private String lastName;
    private AccountCredentials credentials;
    private Role role;
}

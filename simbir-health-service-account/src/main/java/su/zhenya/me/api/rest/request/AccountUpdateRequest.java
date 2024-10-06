package su.zhenya.me.api.rest.request;

import java.util.List;
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
    private List<Role> roles;
}

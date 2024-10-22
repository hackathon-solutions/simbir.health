package su.zhenya.me.api.rest.request;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import su.zhenya.me.account.model.AccountCredentials;
import su.zhenya.me.account.model.Role;

@Data
@Builder
public class AccountCreateRequest {

    private String firstName;
    private String lastName;
    private AccountCredentials credentials;
    private List<Role> roles;
}

package su.zhenya.me.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private AccountId accountId;
    private String firstName;
    private String lastName;
    private AccountCredentials credentials;
    private Role role = Role.USER;
}

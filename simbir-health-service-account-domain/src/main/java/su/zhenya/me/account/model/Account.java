package su.zhenya.me.account.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Role role;
}

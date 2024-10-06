package su.zhenya.me.account.model;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private static final String ACCOUNT_ROLES_STRING_DELIMITER = ";";

    private AccountId accountId;
    private String firstName;
    private String lastName;
    private AccountCredentials credentials;
    private List<Role> roles = List.of(Role.USER);

    public static String listRolesToStringRoles(List<Role> roles) {
        return String.join(ACCOUNT_ROLES_STRING_DELIMITER, roles.stream().map(Role::toString).toList());
    }

    public static List<Role> stringRolesToListRoles(String roles) {
        if (isBlank(roles)) {
            return List.of();
        }

        return Arrays.stream(roles.split(ACCOUNT_ROLES_STRING_DELIMITER)).map(Role::valueOf).toList();
    }
}

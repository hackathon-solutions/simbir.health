package su.zhenya.me.common.security.core.access;

import static su.zhenya.me.account.model.Account.listRolesToStringRoles;
import static su.zhenya.me.account.model.Account.stringRolesToListRoles;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.zhenya.me.account.model.Account;
import su.zhenya.me.account.model.AccountId;
import su.zhenya.me.account.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTokenDescriptor {

    private long accountId;
    private String accountRoles;

    public static AccountTokenDescriptor of(Account account) {
        return new AccountTokenDescriptor(account.getAccountId().getId(), listRolesToStringRoles(account.getRoles()));
    }

    public AccountId getAccountId() {
        return new AccountId(accountId);
    }

    public List<Role> getAccountRoles() {
        return stringRolesToListRoles(accountRoles);
    }
}

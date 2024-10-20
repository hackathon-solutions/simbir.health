package su.zhenya.me.common.security.core.provider;

import java.util.List;
import su.zhenya.me.account.model.Role;
import su.zhenya.me.common.security.core.access.AccountTokenDescriptor;

public interface Authorization {

    boolean isAuthorized();
    String getAccessToken();
    AccountTokenDescriptor getAccountTokenDescriptor();
    List<Role> getRoles();
}

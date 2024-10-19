package su.zhenya.me.common.security.core.provider;

import java.util.List;
import su.zhenya.me.account.model.Role;
import su.zhenya.me.common.security.core.access.AccountTokenDescriptor;
import su.zhenya.me.common.security.core.access.ServiceSecret;

public interface Authorization {

    boolean isAuthorized();
    ServiceSecret getServiceSecret();
    AccountTokenDescriptor getAccountTokenDescriptor();
    List<Role> getRoles();
}

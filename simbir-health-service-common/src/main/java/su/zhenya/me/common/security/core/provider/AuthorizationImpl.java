package su.zhenya.me.common.security.core.provider;

import static java.util.Objects.nonNull;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import su.zhenya.me.account.model.Role;
import su.zhenya.me.common.security.core.access.AccountTokenDescriptor;
import su.zhenya.me.common.security.core.access.ServiceSecret;

@RequiredArgsConstructor
public class AuthorizationImpl implements Authorization {

    private final boolean authorized;
    private final ServiceSecret serviceSecret;
    private final AccountTokenDescriptor accountTokenDescriptor;

    @Override
    public boolean isAuthorized() {
        return authorized;
    }

    @Override
    public ServiceSecret getServiceSecret() {
        return serviceSecret;
    }

    @Override
    public AccountTokenDescriptor getAccountTokenDescriptor() {
        return accountTokenDescriptor;
    }

    @Override
    public List<Role> getRoles() {
        return nonNull(accountTokenDescriptor) && nonNull(accountTokenDescriptor.getAccountRoles())
                ? accountTokenDescriptor.getAccountRoles()
                : Collections.emptyList();
    }
}

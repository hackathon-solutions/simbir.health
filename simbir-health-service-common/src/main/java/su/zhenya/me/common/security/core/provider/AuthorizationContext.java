package su.zhenya.me.common.security.core.provider;

import static java.util.Objects.nonNull;

public class AuthorizationContext {

    private final ThreadLocal<Authorization> authorizationThreadLocal = new ThreadLocal<>();

    public Authorization getAuthorization() {
        if (nonNull(authorizationThreadLocal.get())) {
            return authorizationThreadLocal.get();
        }

        return new AuthorizationImpl(false, null, null);
    }

    public void setAuthorization(Authorization authorization) {
        authorizationThreadLocal.set(authorization);
    }
}

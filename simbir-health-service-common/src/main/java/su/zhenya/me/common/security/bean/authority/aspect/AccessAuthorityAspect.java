package su.zhenya.me.common.security.bean.authority.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import su.zhenya.me.common.security.bean.authority.annotation.AuthorizationContext;
import su.zhenya.me.common.security.bean.authority.annotation.HasRole;

@Aspect
@Component
@RequiredArgsConstructor
public class AccessAuthorityAspect {

    private final su.zhenya.me.common.security.core.provider.AuthorizationContext authorizationContext;

    @Pointcut("@annotation(su.zhenya.me.common.security.bean.authority.annotation.HasRole)")
    public void hasRolePointcut() {}

    @Before("hasRolePointcut()")
    public void hasRoleBefore(JoinPoint jp) {
        HasRole hasRole = ((MethodSignature) jp.getSignature()).getMethod().getAnnotation(HasRole.class);
        if (!authorizationContext.getAuthorization().isAuthorized()
                || Arrays.stream(hasRole.value()).noneMatch(role -> authorizationContext.getAuthorization().getRoles().contains(role))) {
            throwForbidden();
        }
    }

    private static void throwForbidden() {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @Pointcut("@annotation(su.zhenya.me.common.security.bean.authority.annotation.OnlyAuthorized)")
    public void onlyAuthorizedPointcut() {}

    @Before("onlyAuthorizedPointcut()")
    public void onlyAuthorizedBefore() {
        if (!authorizationContext.getAuthorization().isAuthorized()) {
            throwForbidden();
        }
    }

    @Pointcut("execution(* *(.., @su.zhenya.me.common.security.bean.authority.annotation.AuthorizationContext (*), ..))")
    public void injectAuthorizationParameterPointcut() {}

    @Around("injectAuthorizationParameterPointcut()")
    public Object injectAuthorizationAround(ProceedingJoinPoint jp) throws Throwable {
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        Object[] args = jp.getArgs();

        for (int i = 0; i < method.getParameterCount(); i++) {
            if (method.getParameters()[i].isAnnotationPresent(AuthorizationContext.class)
                    && args[i] instanceof su.zhenya.me.common.security.core.provider.Authorization) {
                args[i] = authorizationContext.getAuthorization();
                return jp.proceed(args);
            }
        }

        return jp.proceed(args);
    }
}

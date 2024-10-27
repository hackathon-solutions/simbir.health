package su.zhenya.me.common.validation.bean.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import su.zhenya.me.common.validation.bean.annotation.Validate;
import su.zhenya.me.common.validation.core.Validation;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidationAspect {

    @Pointcut("@annotation(su.zhenya.me.common.validation.bean.annotation.ValidateMethod)")
    public void validatePointcut() {
    }

    @Before("validatePointcut()")
    public void beforeValidate(JoinPoint jp) throws Exception {
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        for (int i = 0; i < method.getParameterCount(); i++) {
            if (method.getParameters()[i].isAnnotationPresent(Validate.class)) {
                Validate validate = method.getParameters()[i].getAnnotation(Validate.class);
                Validation validation = validate.validationClass().getConstructor().newInstance();
                validation.validate(jp.getArgs()[i]);
            }
        }
    }
}

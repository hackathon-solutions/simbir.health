package su.zhenya.me.common.validation.bean.annotation;

import su.zhenya.me.common.validation.core.Validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {

    Class<? extends Validation> validationClass();
}

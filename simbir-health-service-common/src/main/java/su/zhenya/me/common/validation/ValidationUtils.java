package su.zhenya.me.common.validation;

import su.zhenya.me.common.validation.core.ValidationException;

import java.util.function.Supplier;

import static java.util.Objects.isNull;

public class ValidationUtils {

    public static void validateNotNull(Object o) throws ValidationException {
        if (isNull(o)) {
            throw new ValidationException();
        }
    }

    public static void validateThrowIf(Supplier<Boolean> supplier) throws ValidationException {
        if (supplier.get()) {
            throw new ValidationException();
        }
    }
}

package su.zhenya.me.common.exception.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import su.zhenya.me.common.exception.ReplaceException;
import su.zhenya.me.common.security.core.provider.account.AccountNotFoundException;
import su.zhenya.me.common.validation.core.ValidationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAutoHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ReplaceException.class)
    public void replaceException(ReplaceException ex) {
        log.error(ex.getMessage(), ex);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(JWTVerificationException.class)
    public void jwtVerifiedException(Throwable ex) {
        log.error(ex.getMessage(), ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidationException.class, AccountNotFoundException.class, jakarta.validation.ValidationException.class})
    public void accountNotFoundException(Throwable ex) {
        log.error(ex.getMessage(), ex);
    }
}

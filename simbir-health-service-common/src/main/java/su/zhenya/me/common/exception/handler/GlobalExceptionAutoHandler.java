package su.zhenya.me.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import su.zhenya.me.common.exception.ReplaceException;

@RestControllerAdvice
public class GlobalExceptionAutoHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ReplaceException.class)
    public void replaceException() {}
}

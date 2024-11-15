package net.binarypaper.anemic_api.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseStatusException handleIllegalExceptions(RuntimeException ex) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    protected ResponseStatusException handleConflict(RuntimeException ex) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}

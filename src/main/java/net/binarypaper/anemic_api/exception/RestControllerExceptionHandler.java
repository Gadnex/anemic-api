package net.binarypaper.anemic_api.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class RestControllerExceptionHandler {

  @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
  protected ResponseStatusException handleIllegalExceptions(RuntimeException ex) {
    return new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler({NotFoundException.class})
  protected ResponseStatusException handleConflict(RuntimeException ex) {
    return new ResponseStatusException(HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({DataIntegrityViolationException.class})
  protected ResponseStatusException handleConflict(DataIntegrityViolationException ex) {
    if (ex.getCause() instanceof ConstraintViolationException) {
      ConstraintViolationException cve = (ConstraintViolationException) ex.getCause();
      // There is a bug in hibernate where the first character of the foreign key is lost
      String constraintName = "F" + cve.getConstraintName();
      switch (constraintName) {
        case "FK_BACKLOG_ITEM__PRODUCT":
          return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product-id");
        case "FK_BACKLOG_ITEM__SPRINT":
          return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid sprint-id");
        case "FK_SPRINT__PRODUCT":
          return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product-id");
      }
    }
    return new ResponseStatusException(
        HttpStatus.INTERNAL_SERVER_ERROR, "Database exception occurred");
  }
}

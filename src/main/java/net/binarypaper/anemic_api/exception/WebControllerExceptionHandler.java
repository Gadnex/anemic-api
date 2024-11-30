package net.binarypaper.anemic_api.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class WebControllerExceptionHandler {

  @ExceptionHandler(NoResourceFoundException.class)
  public ModelAndView handle(NoResourceFoundException ex) {
    var mv = new ModelAndView();
    mv.addObject("message", "Not Found");
    mv.setViewName("error");
    return mv;
  }
}

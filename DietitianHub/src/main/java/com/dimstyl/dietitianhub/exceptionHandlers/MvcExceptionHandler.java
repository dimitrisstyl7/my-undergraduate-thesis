package com.dimstyl.dietitianhub.exceptionHandlers;

import com.dimstyl.dietitianhub.customExceptions.MvcUserInfoNotFoundException;
import com.dimstyl.dietitianhub.customExceptions.MvcUserNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(value = {MvcUserInfoNotFoundException.class, MvcUserNotFoundException.class})
    protected ModelAndView handleUserNotFoundException() {
        return new ModelAndView("error/user-not-found");
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    protected ModelAndView handleNoResourceFoundException() {
        return new ModelAndView("error/404");
    }

}

package com.dimstyl.dietitianhub.exceptionHandlers;

import com.dimstyl.dietitianhub.customExceptions.ApiTagMismatchException;
import com.dimstyl.dietitianhub.customExceptions.ApiUserInfoNotFoundException;
import com.dimstyl.dietitianhub.customExceptions.ApiUserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApiUserNotFoundException.class, ApiUserInfoNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ErrorResponse handleNotFoundException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase()
        );
    }

    @ExceptionHandler(value = ApiTagMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleTagMismatchException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase()
        );
    }

}

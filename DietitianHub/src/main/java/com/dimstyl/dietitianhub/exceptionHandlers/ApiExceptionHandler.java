package com.dimstyl.dietitianhub.exceptionHandlers;

import com.dimstyl.dietitianhub.exceptions.ApiUserInfoNotFoundException;
import com.dimstyl.dietitianhub.exceptions.TagsMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiUserInfoNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ErrorResponse handleUserInfoNotFoundException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                """
                        The operation could not be completed because the user was not found. \
                        Please try again. If the problem persists, please contact our Support.""",
                "/error/userNotFound"
        );
    }

    @ExceptionHandler(TagsMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleTagsMismatchException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                """
                        Something went wrong while processing the tags. Please try again. \
                        If the problem persists, please contact our Support.""",
                "/error/tagsMismatch"
        );
    }

}

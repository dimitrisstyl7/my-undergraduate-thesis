package com.dimstyl.dietitianhub.exceptionHandlers;

import com.dimstyl.dietitianhub.customExceptions.api.ApiTagCategoriesNotFoundException;
import com.dimstyl.dietitianhub.customExceptions.api.ApiTagsMismatchException;
import com.dimstyl.dietitianhub.customExceptions.api.ApiUserInfoNotFoundException;
import com.dimstyl.dietitianhub.customExceptions.api.ApiUserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApiUserNotFoundException.class, ApiUserInfoNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ErrorResponse handleUserNotFoundException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "The operation could not be completed because the user" +
                        "was not found. Please try again. If the problem persists," +
                        "please contact our Support.",
                "/error/userNotFound"
        );
    }

    @ExceptionHandler(value = ApiTagsMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleTagsMismatchException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Something went wrong while processing the tags." +
                        " Please try again. If the problem persists," +
                        "please contact our Support.",
                "/error/tagsMismatch"
        );
    }

    @ExceptionHandler(value = ApiTagCategoriesNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ErrorResponse handleTagCategoriesNotFoundException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                """
                         The operation could not be completed because no tags were found.\
                         Please try again. If the problem persists,\
                        please contact our Support.""",
                "/error/tagsNotFound"
        );
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleBadRequestException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                """
                        The operation could not be completed because the request was invalid.\
                         Please try again. If the problem persists,\
                        please contact our Support.""",
                "/error/400"
        );
    }

}

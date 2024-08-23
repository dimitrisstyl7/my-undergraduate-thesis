package com.dimstyl.dietitianhub.exceptionHandlers;

import com.dimstyl.dietitianhub.exceptions.*;
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

    @ExceptionHandler(ArticleNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ErrorResponse handleArticleNotFoundException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                """
                        The operation could not be completed because the article was not found. \
                        Please try again. If the problem persists, please contact our Support.""",
                "/error/articleNotFound"
        );
    }

    @ExceptionHandler(AnnouncementNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ErrorResponse handleAnnouncementNotFoundException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                """
                        The operation could not be completed because the announcement was not found. \
                        Please try again. If the problem persists, please contact our Support.""",
                "/error/announcementNotFound"
        );
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    protected ErrorResponse handleAppointmentNotFoundException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                """
                        The operation could not be completed because the appointment was not found. \
                        Please try again. If the problem persists, please contact our Support.""",
                "/error/appointmentNotFound"
        );
    }

}

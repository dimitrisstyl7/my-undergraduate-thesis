package gr.unipi.thesis.dimstyl.exceptionHandlers;

import gr.unipi.thesis.dimstyl.exceptions.announcement.AnnouncementNotFoundException;
import gr.unipi.thesis.dimstyl.exceptions.appointment.AppointmentAlreadyExistsException;
import gr.unipi.thesis.dimstyl.exceptions.appointment.AppointmentIsInTheFutureException;
import gr.unipi.thesis.dimstyl.exceptions.appointment.AppointmentIsInThePastException;
import gr.unipi.thesis.dimstyl.exceptions.appointment.AppointmentNotFoundException;
import gr.unipi.thesis.dimstyl.exceptions.article.ArticleNotFoundException;
import gr.unipi.thesis.dimstyl.exceptions.tag.TagsMismatchException;
import gr.unipi.thesis.dimstyl.exceptions.user.ApiUserInfoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
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
                        Please try again. If the problem persists, please contact our Support \
                        team for assistance.""",
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
                        If the problem persists, please contact our Support team for assistance.""",
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
                        Please try again. If the problem persists, please contact our Support \
                        team for assistance.""",
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
                        Please try again. If the problem persists, please contact our Support team \
                        for assistance.""",
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
                        Please try again. If the problem persists, please contact our Support \
                        team for assistance.""",
                "/error/appointmentNotFound"
        );
    }

    @ExceptionHandler(AppointmentAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    protected ErrorResponse handleAppointmentAlreadyExistsException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                """
                        An appointment is already scheduled for this date and time. \
                        Please decline this pending appointment or cancel the existing \
                        one before approving this request.""",
                null
        );
    }

    @ExceptionHandler(AppointmentIsInThePastException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleAppointmentIsInThePastException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                """
                        You cannot cancel an appointment that has already passed.""",
                null
        );
    }

    @ExceptionHandler(AppointmentIsInTheFutureException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleAppointmentIsInTheFutureException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                """
                        You cannot mark as complete an appointment that is scheduled for the future.""",
                null
        );
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    protected ErrorResponse handleDisabledException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                """
                        Your account has been disabled. \
                        If you are a new user, please check your inbox for an activation email. \
                        For existing users, please contact our Support team for assistance.""",
                null
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    protected ErrorResponse handleBadCredentialsException() {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                """
                        The username or password you entered is incorrect. Please try again.""",
                null
        );
    }

}

package gr.unipi.thesis.dimstyl.exceptionHandlers;

import gr.unipi.thesis.dimstyl.exceptions.dietPlan.DietPlanNotFoundException;
import gr.unipi.thesis.dimstyl.exceptions.storage.FileDeletionException;
import gr.unipi.thesis.dimstyl.exceptions.storage.FileStorageException;
import gr.unipi.thesis.dimstyl.exceptions.storage.StorageFileNotFoundException;
import gr.unipi.thesis.dimstyl.exceptions.user.RegistrationFailedException;
import gr.unipi.thesis.dimstyl.exceptions.user.UserNotFoundException;
import gr.unipi.thesis.dimstyl.exceptions.user.WebUserInfoNotFoundException;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;

@ControllerAdvice
public class WebExceptionHandler {

    @ExceptionHandler({WebUserInfoNotFoundException.class, UserNotFoundException.class})
    protected ResponseEntity<Void> handleUserNotFoundException() {
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .location(URI.create("/error/userNotFound"))
                .build();
    }

    @ExceptionHandler(FileStorageException.class)
    protected ResponseEntity<Void> handleFileStorageException() {
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .location(URI.create("/error/dietPlanUploadFailed"))
                .build();
    }

    @ExceptionHandler({DietPlanNotFoundException.class, StorageFileNotFoundException.class})
    protected ResponseEntity<Void> handleDietPlanNotFoundException() {
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .location(URI.create("/error/dietPlanNotFound"))
                .build();
    }

    @ExceptionHandler(FileDeletionException.class)
    protected ResponseEntity<Void> handleFileDeletionException() {
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .location(URI.create("/error/dietPlanDeleteFailed"))
                .build();
    }

    @ExceptionHandler({RegistrationFailedException.class, MessagingException.class})
    protected ModelAndView handleRegistrationFailedException() {
        return new ModelAndView("error/registration-failed");
    }

}

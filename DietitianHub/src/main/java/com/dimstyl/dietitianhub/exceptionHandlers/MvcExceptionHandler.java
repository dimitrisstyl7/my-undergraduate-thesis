package com.dimstyl.dietitianhub.exceptionHandlers;

import com.dimstyl.dietitianhub.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler({MvcUserInfoNotFoundException.class, UserNotFoundException.class})
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

    @ExceptionHandler(RegistrationFailedException.class)
    protected ModelAndView handleRegistrationFailedException() {
        return new ModelAndView("error/registration-failed");
    }

}

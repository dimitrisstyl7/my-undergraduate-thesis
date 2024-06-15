package com.dimstyl.dietitianhub.customExceptions.api;

public class ApiTagsMismatchException extends RuntimeException {

    public ApiTagsMismatchException(String message) {
        super(message);
    }
}

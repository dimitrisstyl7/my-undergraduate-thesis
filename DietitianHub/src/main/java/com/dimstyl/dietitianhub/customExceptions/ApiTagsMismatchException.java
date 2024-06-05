package com.dimstyl.dietitianhub.customExceptions;

public class ApiTagsMismatchException extends RuntimeException {

    public ApiTagsMismatchException(String message) {
        super(message);
    }
}

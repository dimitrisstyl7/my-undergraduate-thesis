package com.dimstyl.dietitianhub.customExceptions;

public class ApiTagMismatchException extends RuntimeException {

    public ApiTagMismatchException(String message) {
        super(message);
    }
}

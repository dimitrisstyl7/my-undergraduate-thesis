package com.dimstyl.dietitianhub.customExceptions;

public class ApiUserNotFoundException extends RuntimeException {

    public ApiUserNotFoundException(String message) {
        super(message);
    }

}

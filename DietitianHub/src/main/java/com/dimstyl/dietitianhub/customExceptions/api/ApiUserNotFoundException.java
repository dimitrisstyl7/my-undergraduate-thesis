package com.dimstyl.dietitianhub.customExceptions.api;

public class ApiUserNotFoundException extends RuntimeException {

    public ApiUserNotFoundException(String message) {
        super(message);
    }

}

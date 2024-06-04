package com.dimstyl.dietitianhub.customExceptions;

public class ApiUserInfoNotFoundException extends RuntimeException {

    public ApiUserInfoNotFoundException(String message) {
        super(message);
    }

}

package com.dimstyl.dietitianhub.customExceptions;

public class MvcUserNotFoundException extends RuntimeException {

    public MvcUserNotFoundException(String message) {
        super(message);
    }

}

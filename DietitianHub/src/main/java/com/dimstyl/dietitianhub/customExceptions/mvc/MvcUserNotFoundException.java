package com.dimstyl.dietitianhub.customExceptions.mvc;

public class MvcUserNotFoundException extends RuntimeException {

    public MvcUserNotFoundException(String message) {
        super(message);
    }

}

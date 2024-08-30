package com.dimstyl.dietitianhub.exceptions;

public class AppointmentAlreadyExistsException extends RuntimeException {

    public AppointmentAlreadyExistsException(String message) {
        super(message);
    }

}

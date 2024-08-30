package com.dimstyl.dietitianhub.exceptions.appointment;

public class AppointmentAlreadyExistsException extends RuntimeException {

    public AppointmentAlreadyExistsException(String message) {
        super(message);
    }

}

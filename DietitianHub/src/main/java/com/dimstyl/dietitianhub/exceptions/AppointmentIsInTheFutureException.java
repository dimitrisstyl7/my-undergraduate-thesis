package com.dimstyl.dietitianhub.exceptions;

public class AppointmentIsInTheFutureException extends RuntimeException {

    public AppointmentIsInTheFutureException(String message) {
        super(message);
    }

}

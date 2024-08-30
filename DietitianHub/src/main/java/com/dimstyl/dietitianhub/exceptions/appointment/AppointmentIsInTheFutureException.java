package com.dimstyl.dietitianhub.exceptions.appointment;

public class AppointmentIsInTheFutureException extends RuntimeException {

    public AppointmentIsInTheFutureException(String message) {
        super(message);
    }

}

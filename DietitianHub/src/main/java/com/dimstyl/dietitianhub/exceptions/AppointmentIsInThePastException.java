package com.dimstyl.dietitianhub.exceptions;

public class AppointmentIsInThePastException extends RuntimeException {

    public AppointmentIsInThePastException(String message) {
        super(message);
    }

}

package com.dimstyl.dietitianhub.exceptions.appointment;

public class AppointmentIsInThePastException extends RuntimeException {

    public AppointmentIsInThePastException(String message) {
        super(message);
    }

}

package gr.unipi.thesis.dimstyl.exceptions.appointment;

public class AppointmentIsInThePastException extends RuntimeException {

    public AppointmentIsInThePastException(String message) {
        super(message);
    }

}
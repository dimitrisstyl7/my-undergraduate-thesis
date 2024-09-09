package gr.unipi.thesis.dimstyl.exceptions.appointment;

public class AppointmentIsInTheFutureException extends RuntimeException {

    public AppointmentIsInTheFutureException(String message) {
        super(message);
    }

}

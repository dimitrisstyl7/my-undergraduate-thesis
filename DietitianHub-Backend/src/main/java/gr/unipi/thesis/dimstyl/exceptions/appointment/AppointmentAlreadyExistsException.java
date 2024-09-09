package gr.unipi.thesis.dimstyl.exceptions.appointment;

public class AppointmentAlreadyExistsException extends RuntimeException {

    public AppointmentAlreadyExistsException(String message) {
        super(message);
    }

}

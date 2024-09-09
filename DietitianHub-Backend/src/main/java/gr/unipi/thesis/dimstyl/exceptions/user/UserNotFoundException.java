package gr.unipi.thesis.dimstyl.exceptions.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

}

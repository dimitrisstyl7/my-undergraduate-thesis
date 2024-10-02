package gr.unipi.thesis.dimstyl.exceptions.storage;

public class WebStorageFileNotFoundException extends RuntimeException {

    public WebStorageFileNotFoundException(String message) {
        super(message);
    }

    public WebStorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}

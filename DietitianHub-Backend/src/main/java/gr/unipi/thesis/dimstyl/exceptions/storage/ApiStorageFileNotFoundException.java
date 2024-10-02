package gr.unipi.thesis.dimstyl.exceptions.storage;

public class ApiStorageFileNotFoundException extends RuntimeException {

    public ApiStorageFileNotFoundException(String message) {
        super(message);
    }

    public ApiStorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

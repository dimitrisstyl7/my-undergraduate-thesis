package com.dimstyl.dietitianhub.customExceptions;

public class ApiTagCategoriesNotFoundException extends RuntimeException {

    public ApiTagCategoriesNotFoundException(String message) {
        super(message);
    }
}

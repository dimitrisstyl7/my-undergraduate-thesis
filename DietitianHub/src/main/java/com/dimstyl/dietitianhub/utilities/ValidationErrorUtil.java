package com.dimstyl.dietitianhub.utilities;

import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Optional;

@UtilityClass
public final class ValidationErrorUtil {

    public static Optional<String> getTitleError(BindingResult result) {
        ObjectError fieldError = result.getFieldError("title");
        ObjectError globalError = result.getGlobalError();

        if (fieldError != null && fieldError.getDefaultMessage() != null) {
            return Optional.ofNullable(fieldError.getDefaultMessage());
        } else if (globalError != null && globalError.getDefaultMessage() != null) {
            return Optional.ofNullable(globalError.getDefaultMessage());
        } else return Optional.empty();
    }

    public static Optional<String> getContentError(BindingResult result) {
        ObjectError error = result.getFieldError("content");

        if (error != null && error.getDefaultMessage() != null) {
            return Optional.ofNullable(error.getDefaultMessage());
        } else return Optional.empty();
    }

}

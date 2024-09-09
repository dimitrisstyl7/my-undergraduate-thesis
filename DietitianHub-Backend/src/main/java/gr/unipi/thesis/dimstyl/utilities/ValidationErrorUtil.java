package gr.unipi.thesis.dimstyl.utilities;

import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Optional;

@UtilityClass
public final class ValidationErrorUtil {

    public static Optional<String> getTitleError(BindingResult result) {
        ObjectError fieldError = result.getFieldError("title"); // @NotBlank and @Size constraints.
        ObjectError globalError = result.getGlobalError(); // @UniqueTitle constraint.

        if (fieldError != null) return Optional.ofNullable(fieldError.getDefaultMessage());
        else if (globalError != null) return Optional.ofNullable(globalError.getDefaultMessage());
        else return Optional.empty();
    }

    public static Optional<String> getError(String field, BindingResult result) {
        ObjectError error = result.getFieldError(field);
        if (error != null) return Optional.ofNullable(error.getDefaultMessage());
        else return Optional.empty();
    }

}

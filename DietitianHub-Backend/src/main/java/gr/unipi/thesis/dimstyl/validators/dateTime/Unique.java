package gr.unipi.thesis.dimstyl.validators.dateTime;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateTimeExistenceValidator.class)
public @interface Unique {

    String message() default "An appointment is already scheduled at this date and time";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

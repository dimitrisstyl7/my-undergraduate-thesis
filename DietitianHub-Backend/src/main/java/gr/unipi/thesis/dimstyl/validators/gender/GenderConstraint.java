package gr.unipi.thesis.dimstyl.validators.gender;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderValidator.class)
public @interface GenderConstraint {

    String message() default "Acceptable gender values are 'MALE' or 'FEMALE'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

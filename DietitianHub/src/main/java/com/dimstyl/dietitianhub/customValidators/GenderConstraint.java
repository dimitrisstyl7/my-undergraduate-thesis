package com.dimstyl.dietitianhub.customValidators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderValidator.class)
public @interface GenderConstraint {

    String message() default "Acceptable gender values are 'M' or 'F'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

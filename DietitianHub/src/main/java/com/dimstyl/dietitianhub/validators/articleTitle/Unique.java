package com.dimstyl.dietitianhub.validators.articleTitle;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TitleExistenceValidator.class)
public @interface Unique {

    String message() default "Title already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
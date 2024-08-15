package com.dimstyl.dietitianhub.validators.articleTitle;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TitleExistenceValidator.class)
public @interface UniqueTitle {

    String message() default "Title already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
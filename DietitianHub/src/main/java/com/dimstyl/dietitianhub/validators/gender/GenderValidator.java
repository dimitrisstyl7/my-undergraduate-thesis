package com.dimstyl.dietitianhub.validators.gender;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class GenderValidator implements ConstraintValidator<GenderConstraint, Character> {

    @Override
    public void initialize(GenderConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

    }

    @Override
    public boolean isValid(Character character, ConstraintValidatorContext constraintValidatorContext) {
        return character != null && (List.of('M', 'F').contains(character));
    }

}

package gr.unipi.thesis.dimstyl.validators.gender;

import gr.unipi.thesis.dimstyl.enums.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.EnumSet;

public class GenderValidator implements ConstraintValidator<GenderConstraint, Gender> {

    @Override
    public void initialize(GenderConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

    }

    @Override
    public boolean isValid(Gender gender, ConstraintValidatorContext constraintValidatorContext) {
        return gender != null && EnumSet.allOf(Gender.class).contains(gender);
    }

}

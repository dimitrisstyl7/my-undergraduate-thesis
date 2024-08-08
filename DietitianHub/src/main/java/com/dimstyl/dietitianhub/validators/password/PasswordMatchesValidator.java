package com.dimstyl.dietitianhub.validators.password;

import com.dimstyl.dietitianhub.dtos.ClientCredentialChangeDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, ClientCredentialChangeDto> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ClientCredentialChangeDto credentialChangeDto, ConstraintValidatorContext constraintValidatorContext) {
        String password = credentialChangeDto.getPassword();
        String confirmPassword = credentialChangeDto.getConfirmPassword();

        if (password == null || confirmPassword == null) {
            return false;
        }

        return password.equals(confirmPassword);
    }

}

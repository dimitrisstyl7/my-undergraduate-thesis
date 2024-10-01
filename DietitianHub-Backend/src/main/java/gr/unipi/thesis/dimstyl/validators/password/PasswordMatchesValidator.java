package gr.unipi.thesis.dimstyl.validators.password;

import gr.unipi.thesis.dimstyl.dtos.web.WebClientCredentialChangeDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, WebClientCredentialChangeDto> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(WebClientCredentialChangeDto credentialChangeDto, ConstraintValidatorContext constraintValidatorContext) {
        String password = credentialChangeDto.getPassword();
        String confirmPassword = credentialChangeDto.getConfirmPassword();

        if (password == null || confirmPassword == null) {
            return false;
        }

        return password.equals(confirmPassword);
    }

}

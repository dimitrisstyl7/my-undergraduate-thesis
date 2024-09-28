package gr.unipi.thesis.dimstyl.validators.username;

import gr.unipi.thesis.dimstyl.security.CustomUserDetailsService;
import gr.unipi.thesis.dimstyl.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsernameExistenceValidator implements ConstraintValidator<Unique, String> {

    private final UserService userService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String currentUsername = userDetailsService.getUserDetails().getUsername();
        return currentUsername.equals(s) || !userService.usernameExists(s);
    }

}

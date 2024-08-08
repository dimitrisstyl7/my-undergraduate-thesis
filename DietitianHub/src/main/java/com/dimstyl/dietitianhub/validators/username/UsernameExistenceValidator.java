package com.dimstyl.dietitianhub.validators.username;

import com.dimstyl.dietitianhub.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import static com.dimstyl.dietitianhub.security.CustomUserDetailsService.getUserDetails;

@RequiredArgsConstructor
public class UsernameExistenceValidator implements ConstraintValidator<Unique, String> {

    private final UserService userService;

    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String currentUsername = getUserDetails().getUsername();
        return currentUsername.equals(s) || !userService.usernameExists(s);
    }

}

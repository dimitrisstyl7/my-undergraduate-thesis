package com.dimstyl.dietitianhub.validators.dateTime;

import com.dimstyl.dietitianhub.services.AppointmentService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class DateTimeExistenceValidator implements ConstraintValidator<Unique, LocalDateTime> {

    private final AppointmentService appointmentService;

    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        return !appointmentService.existsByScheduledDateTime(localDateTime);
    }

}

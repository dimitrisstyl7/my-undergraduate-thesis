package gr.unipi.thesis.dimstyl.validators.dateTime;

import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import gr.unipi.thesis.dimstyl.services.AppointmentService;
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
        return !appointmentService.existsByAppointmentDateTimeAndStatus(localDateTime, AppointmentStatus.SCHEDULED);
    }

}

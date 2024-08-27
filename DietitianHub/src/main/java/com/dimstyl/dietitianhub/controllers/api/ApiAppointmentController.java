package com.dimstyl.dietitianhub.controllers.api;

import com.dimstyl.dietitianhub.dtos.AppointmentDto;
import com.dimstyl.dietitianhub.enums.AppointmentStatus;
import com.dimstyl.dietitianhub.services.AppointmentService;
import com.dimstyl.dietitianhub.utilities.ValidationErrorUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class ApiAppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAppointments() {
        return ResponseEntity.ok(
                appointmentService.getAppointmentsByStatusAndScheduledDateTimeAfter(
                        AppointmentStatus.SCHEDULED,
                        java.time.LocalDateTime.now()
                )
        );
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createAppointment(@Valid @RequestBody AppointmentDto appointmentDto,
                                                                 BindingResult result) {
        // If there are no errors, proceed with creating the appointment.
        if (!result.hasErrors()) {
            AppointmentDto newAppointmentDto = appointmentService.createAppointment(appointmentDto);
            return ResponseEntity.ok(Map.of(
                    "id", String.valueOf(newAppointmentDto.id()),
                    "formattedScheduledDateTime", newAppointmentDto.formattedScheduledDateTime(),
                    "clientFullName", newAppointmentDto.clientFullName())
            );
        }

        // Otherwise, return a bad request.
        Optional<String> titleError = ValidationErrorUtil.getTitleError(result);
        Optional<String> startError = ValidationErrorUtil.getError("start", result);
        Optional<String> clientError = ValidationErrorUtil.getError("clientId", result);
        Map<String, String> errors = new HashMap<>() {
            {
                titleError.ifPresent(s -> put("title", s));
                startError.ifPresent(s -> put("start", s));
                clientError.ifPresent(s -> put("client", s));
            }
        };

        // If errors map is empty (no default message found), return a bad request without a body.
        // Otherwise, return a bad request with the errors map in the body.
        return errors.isEmpty() ? ResponseEntity.badRequest().build() : ResponseEntity.badRequest().body(errors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateAppointment(@PathVariable("id") int id,
                                                                 @Valid @RequestBody AppointmentDto appointmentDto,
                                                                 BindingResult result) {
        // If there is an error in title, return a bad request.
        if (result.hasFieldErrors("title")) {
            Optional<String> titleError = ValidationErrorUtil.getTitleError(result);

            // If default message is found, return a bad request with the title error in the body.
            // Otherwise, return a bad request without a body.
            return titleError
                    .map(s -> ResponseEntity.badRequest().body(Map.of("title", s)))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        }

        // Otherwise, continue with updating the appointment.
        appointmentService.updateAppointment(id, appointmentDto);
        return ResponseEntity.noContent().build();
    }

}

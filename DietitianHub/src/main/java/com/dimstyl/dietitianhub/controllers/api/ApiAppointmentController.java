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
        return ResponseEntity.ok(appointmentService.getAppointmentsByStatus(AppointmentStatus.SCHEDULED));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateAppointment(@PathVariable("id") int id,
                                                                 @Valid @RequestBody AppointmentDto appointmentDto,
                                                                 BindingResult result) {
        // If there is an error in title, return a bad request.
        if (result.hasErrors()) {
            Optional<String> titleError = ValidationErrorUtil.getTitleError(result);

            // If default message is found, return a bad request with the title error in the body.
            // Otherwise, return a bad request without a body.
            return titleError
                    .map(s -> ResponseEntity.badRequest().body(Map.of("title", s)))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        }

        // Otherwise, continue with the update of the appointment.
        appointmentService.updateAppointment(id, appointmentDto);
        return ResponseEntity.ok().build();
    }

}

package gr.unipi.thesis.dimstyl.controllers.api;

import gr.unipi.thesis.dimstyl.dtos.AppointmentDto;
import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import gr.unipi.thesis.dimstyl.services.AppointmentService;
import gr.unipi.thesis.dimstyl.utilities.ValidationErrorUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
                appointmentService.getAppointmentsByStatusesAfterGivenAppointmentDateTime(
                        List.of(AppointmentStatus.SCHEDULED, AppointmentStatus.COMPLETED),
                        LocalDate.now().atStartOfDay()
                )
        );
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createAppointment(@Valid @RequestBody AppointmentDto appointmentDto,
                                                                 BindingResult result) {
        // If there are no errors, proceed with creating the appointment
        if (!result.hasErrors()) {
            appointmentService.createAppointment(appointmentDto);
            return ResponseEntity.noContent().build();
        }

        // Otherwise, return a bad request
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
        // If there is an error in title, return a bad request
        if (result.hasFieldErrors("title")) {
            Optional<String> titleError = ValidationErrorUtil.getTitleError(result);

            // If a default message is found, return a bad request with the title error in the body.
            // Otherwise, return a bad request without a body.
            return titleError
                    .map(s -> ResponseEntity.badRequest().body(Map.of("title", s)))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        }

        // Otherwise, continue with updating the appointment
        appointmentService.updateAppointment(id, appointmentDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> completeAppointment(@PathVariable("id") int id) {
        appointmentService.completeAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable("id") int id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/decline")
    public ResponseEntity<Void> declineAppointment(@PathVariable("id") int id) {
        appointmentService.declineAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<Void> approveAppointment(@PathVariable("id") int id) {
        appointmentService.approveAppointment(id);
        return ResponseEntity.noContent().build();
    }

}

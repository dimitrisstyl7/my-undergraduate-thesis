package gr.unipi.thesis.dimstyl.controllers.api;

import gr.unipi.thesis.dimstyl.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class ApiAppointmentController {

    private final AppointmentService appointmentService;

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable("id") int id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }

}

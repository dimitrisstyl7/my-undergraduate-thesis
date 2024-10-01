package gr.unipi.thesis.dimstyl.controllers.api;

import gr.unipi.thesis.dimstyl.dtos.api.ApiAppointmentDto;
import gr.unipi.thesis.dimstyl.dtos.api.AppointmentsResponse;
import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import gr.unipi.thesis.dimstyl.security.CustomUserDetailsService;
import gr.unipi.thesis.dimstyl.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class ApiAppointmentController {

    private final CustomUserDetailsService userDetailsService;
    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<AppointmentsResponse> getAppointments() {
        LocalDateTime now = LocalDateTime.now();
        String username = userDetailsService.getUserDetails().getUsername();
        List<ApiAppointmentDto> scheduledAppointments =
                appointmentService.getLatest5AppointmentsByUsernameAndStatusAfterGivenAppointmentDateTime(
                        username,
                        AppointmentStatus.SCHEDULED,
                        now
                );
        List<ApiAppointmentDto> pendingAppointments =
                appointmentService.getLatest5AppointmentsByUsernameAndStatusAfterGivenAppointmentDateTime(
                        username,
                        AppointmentStatus.PENDING,
                        now
                );
        List<ApiAppointmentDto> completedAppointments =
                appointmentService.getLatest5AppointmentsByUsernameAndStatus(username, AppointmentStatus.COMPLETED);
        List<ApiAppointmentDto> declinedAppointments =
                appointmentService.getLatest5AppointmentsByUsernameAndStatus(username, AppointmentStatus.DECLINED);
        List<ApiAppointmentDto> cancelledAppointments =
                appointmentService.getLatest5AppointmentsByUsernameAndStatus(username, AppointmentStatus.CANCELLED);

        AppointmentsResponse appointmentsResponse = AppointmentsResponse.builder()
                .scheduledAppointments(scheduledAppointments)
                .pendingAppointments(pendingAppointments)
                .completedAppointments(completedAppointments)
                .declinedAppointments(declinedAppointments)
                .cancelledAppointments(cancelledAppointments)
                .build();

        return ResponseEntity.ok(appointmentsResponse);
    }

    @PostMapping
    public ResponseEntity<ApiAppointmentDto> createAppointment(@RequestBody ApiAppointmentDto apiAppointmentDto) {
        return ResponseEntity.ok(appointmentService.createAppointment(apiAppointmentDto.appointmentDateTime()));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable("id") int id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }

}

package gr.unipi.thesis.dimstyl.controllers.mvc;

import gr.unipi.thesis.dimstyl.dtos.AppointmentDto;
import gr.unipi.thesis.dimstyl.dtos.ClientDto;
import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import gr.unipi.thesis.dimstyl.services.AppointmentService;
import gr.unipi.thesis.dimstyl.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class MvcAppointmentController {

    private final UserService userService;
    private final AppointmentService appointmentService;

    @GetMapping
    public String appointmentsPage(Model model) {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        String minDateTime = dateTimeNow.toString().substring(0, 16);
        List<ClientDto> clients = userService.getAllClients();
        List<AppointmentDto> todaysAppointments =
                appointmentService.getAppointmentsByStatusesWithinAppointmentDateTimeRange(
                        List.of(AppointmentStatus.SCHEDULED, AppointmentStatus.COMPLETED),
                        LocalDate.now().atStartOfDay(),
                        LocalDate.now().atStartOfDay().plusHours(23).plusMinutes(59)
                );
        List<AppointmentDto> pendingAppointments =
                appointmentService.getAppointmentsByStatusAfterGivenAppointmentDateTimeOrdered(
                        AppointmentStatus.PENDING,
                        dateTimeNow
                );

        model.addAttribute("clients", clients);
        model.addAttribute("minDateTime", minDateTime);
        model.addAttribute("dateTimeNow", dateTimeNow);
        model.addAttribute("todaysAppointments", todaysAppointments);
        model.addAttribute("pendingAppointments", pendingAppointments);

        return "appointments";
    }

}

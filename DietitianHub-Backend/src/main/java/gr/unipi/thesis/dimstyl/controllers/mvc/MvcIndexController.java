package gr.unipi.thesis.dimstyl.controllers.mvc;

import gr.unipi.thesis.dimstyl.dtos.AppointmentDto;
import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import gr.unipi.thesis.dimstyl.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MvcIndexController {

    private final AppointmentService appointmentService;

    @GetMapping({"/", "/index"})
    public String indexPage(Model model) {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        List<AppointmentDto> upcomingAppointments =
                appointmentService.getLatest5AppointmentsByStatusAfterGivenAppointmentDateTime(
                        AppointmentStatus.SCHEDULED,
                        dateTimeNow
                );
        List<AppointmentDto> declinedAppointments =
                appointmentService.getLatest5AppointmentsByStatus(AppointmentStatus.DECLINED);
        List<AppointmentDto> completedAppointments =
                appointmentService.getLatest5AppointmentsByStatus(AppointmentStatus.COMPLETED);
        List<AppointmentDto> cancelledAppointments =
                appointmentService.getLatest5AppointmentsByStatus(AppointmentStatus.CANCELLED);

        model.addAttribute("upcomingAppointments", upcomingAppointments);
        model.addAttribute("declinedAppointments", declinedAppointments);
        model.addAttribute("completedAppointments", completedAppointments);
        model.addAttribute("cancelledAppointments", cancelledAppointments);

        return "index";
    }

}
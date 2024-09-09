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
        List<AppointmentDto> pendingAppointments =
                appointmentService.getAppointmentsByStatusAndScheduledDateTimeIsAfterOrderByScheduledDateTime(
                        AppointmentStatus.PENDING,
                        dateTimeNow
                );
        List<AppointmentDto> upcomingAppointments =
                appointmentService.getFirst5AppointmentsByStatusAndScheduledDateTimeIsAfter(
                        AppointmentStatus.SCHEDULED,
                        dateTimeNow
                );
        List<AppointmentDto> declinedAppointments =
                appointmentService.getFirst5AppointmentsByStatus(AppointmentStatus.DECLINED);
        List<AppointmentDto> completedAppointments =
                appointmentService.getFirst5AppointmentsByStatus(AppointmentStatus.COMPLETED);
        List<AppointmentDto> cancelledAppointments =
                appointmentService.getFirst5AppointmentsByStatus(AppointmentStatus.CANCELLED);

        model.addAttribute("pendingAppointments", pendingAppointments);
        model.addAttribute("upcomingAppointments", upcomingAppointments);
        model.addAttribute("declinedAppointments", declinedAppointments);
        model.addAttribute("completedAppointments", completedAppointments);
        model.addAttribute("cancelledAppointments", cancelledAppointments);

        return "index";
    }

}
package com.dimstyl.dietitianhub.controllers.mvc;

import com.dimstyl.dietitianhub.dtos.AppointmentDto;
import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.enums.AppointmentStatus;
import com.dimstyl.dietitianhub.services.AppointmentService;
import com.dimstyl.dietitianhub.services.UserService;
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
                appointmentService.getAppointmentsByStatusAndScheduledDateTimeIsBetween(
                        List.of(AppointmentStatus.SCHEDULED, AppointmentStatus.COMPLETED),
                        LocalDate.now().atStartOfDay(),
                        LocalDate.now().atStartOfDay().plusHours(23).plusMinutes(59)
                );
        List<AppointmentDto> pendingAppointments =
                appointmentService.getAppointmentsByStatusAndScheduledDateTimeIsAfterOrderByScheduledDateTime(
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

package com.dimstyl.dietitianhub.controllers.mvc;

import com.dimstyl.dietitianhub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class MvcAppointmentController {

    private final UserService userService;

    @GetMapping
    public String appointmentsPage(Model model) {

        model.addAttribute("clients", userService.getAllClients());
        model.addAttribute("minDateTime", LocalDateTime.now().toString().substring(0, 16));
        return "appointments";
    }

}

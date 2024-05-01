package com.dimstyl.dietitianhub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppointmentsController {
    @GetMapping("/appointments")
    public String appointmentsPage() {
        return "appointments";
    }
}

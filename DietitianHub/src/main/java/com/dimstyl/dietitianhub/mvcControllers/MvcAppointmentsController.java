package com.dimstyl.dietitianhub.mvcControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcAppointmentsController {

    @GetMapping("/appointments")
    public String appointmentsPage() {
        return "appointments";
    }

}

package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Endpoints;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppointmentsController {
    @GetMapping(Endpoints.APPOINTMENTS_ENDPOINT)
    public String appointmentsPage() {
        return Endpoints.APPOINTMENTS_HTML;
    }
}

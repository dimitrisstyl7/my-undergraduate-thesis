package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppointmentsController {
    @GetMapping(Constants.APPOINTMENTS_URL)
    public String appointmentsPage() {
        return Constants.APPOINTMENTS_HTML;
    }
}

package com.dimstyl.dietitianhub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.dimstyl.dietitianhub.constants.Endpoints.APPOINTMENTS_ENDPOINT;
import static com.dimstyl.dietitianhub.constants.HtmlFileNames.APPOINTMENTS_HTML;

@Controller
public class AppointmentsController {
    @GetMapping(APPOINTMENTS_ENDPOINT)
    public String appointmentsPage() {
        return APPOINTMENTS_HTML;
    }
}

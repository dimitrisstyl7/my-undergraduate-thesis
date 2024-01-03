package com.dimstyl.dietitianhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.dimstyl.dietitianhub.constants.Endpoints.VIEW_CLIENTS_ENDPOINT;
import static com.dimstyl.dietitianhub.constants.HtmlFileNames.VIEW_CLIENTS_HTML;

@Controller
public class ClientsController {
    @GetMapping(VIEW_CLIENTS_ENDPOINT)
    public String viewClientsPage() {
        return VIEW_CLIENTS_HTML;
    }
}

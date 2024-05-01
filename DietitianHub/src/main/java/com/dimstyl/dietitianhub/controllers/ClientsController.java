package com.dimstyl.dietitianhub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientsController {
    @GetMapping("/clients")
    public String viewClientsPage() {
        return "view-clients";
    }
}

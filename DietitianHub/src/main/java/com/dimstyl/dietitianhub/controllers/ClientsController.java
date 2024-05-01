package com.dimstyl.dietitianhub.controllers;

import com.dimstyl.dietitianhub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientsController {
    private final UserService userService;

    @GetMapping
    public String viewClientsPage(Model model) {
        model.addAttribute("clients", userService.getAllClients());
        return "clients/view-clients";
    }
}

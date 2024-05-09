package com.dimstyl.dietitianhub.controllers;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientsController {
    private final UserService userService;

    @GetMapping
    public String viewClientsPage(Model model) {
        model.addAttribute("clients", userService.getAllClients());
        model.addAttribute("newClient", new ClientDto());
        return "clients/view-clients";
    }

    @PostMapping("/register")
    public String registerClient(
            @Valid @ModelAttribute("newClient") ClientDto clientDto,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clients", userService.getAllClients());
            model.addAttribute("validationFailed", true);
            return "clients/view-clients";
        }
        userService.registerClient(clientDto);
        return "redirect:/clients";
    }
}

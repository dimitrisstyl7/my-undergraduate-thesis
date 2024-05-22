package com.dimstyl.dietitianhub.controllers;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
            model.addAttribute("registrationValidationFailed", true);
            return "clients/view-clients";
        }
        userService.registerClient(clientDto);
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id) {
        userService.deleteClient(id);
        return "redirect:/clients";
    }
}

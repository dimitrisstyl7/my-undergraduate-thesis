package com.dimstyl.dietitianhub.controllers;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.services.UserInfoService;
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
    private final UserInfoService userInfoService;

    @GetMapping
    public String viewClientsPage(Model model) {
        model.addAttribute("clients", userService.getAllClients());
        model.addAttribute("client", new ClientDto());
        return "view-clients";
    }

    @PostMapping("/register")
    public String registerClient(
            @Valid @ModelAttribute("client") ClientDto clientDto,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clients", userService.getAllClients());
            model.addAttribute("dateOfBirth", clientDto.getDateOfBirth());
            model.addAttribute("registerValidationsFailed", true);
            return "view-clients";
        }
        userService.registerClient(clientDto);
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id) {
        userService.disableClient(id);
        return "redirect:/clients";
    }

    @PostMapping("/update/{id}")
    public String updateClient(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("client") ClientDto clientDto,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clients", userService.getAllClients());
            model.addAttribute("editedClient", clientDto);
            model.addAttribute("updateValidationsFailed", true);
            return "view-clients";
        }
        User user = userService.findById(id);
        userInfoService.update(clientDto, user);
        return "redirect:/clients";
    }
}

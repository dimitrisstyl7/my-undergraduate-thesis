package com.dimstyl.dietitianhub.mvcControllers;

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
public class MvcClientsController {
    private final UserService userService;
    private final UserInfoService userInfoService;

    @GetMapping
    public String viewClientsPage(Model model) {
        model.addAttribute("clients", userService.getAllClients());
        model.addAttribute("client", new ClientDto());
        return "view-clients";
    }

    @PostMapping("/registerClient")
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

    @PostMapping("/updateClient/{id}")
    public String updateClient(
            @PathVariable("id") Integer id,
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
        userInfoService.updateUserInfo(clientDto, user);
        return "redirect:/clients";
    }
}

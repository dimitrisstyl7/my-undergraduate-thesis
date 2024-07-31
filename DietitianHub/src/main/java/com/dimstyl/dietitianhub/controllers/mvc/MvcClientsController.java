package com.dimstyl.dietitianhub.controllers.mvc;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.services.TagCategoryService;
import com.dimstyl.dietitianhub.services.UserInfoService;
import com.dimstyl.dietitianhub.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class MvcClientsController {

    private final UserService userService;
    private final UserInfoService userInfoService;
    private final TagCategoryService tagCategoryService;

    @GetMapping
    public String viewClientsPage(@RequestParam("success") Optional<String> success,
                                  @RequestParam("tagsUpdateSuccess") Optional<String> tagsUpdateSuccess,
                                  @ModelAttribute("flashAttribute") Object flashAttribute,
                                  Model model) {
        if (success.isPresent() &&
                flashAttribute instanceof String alertMessage && !alertMessage.isEmpty()) {
            model.addAttribute("alertMessage", alertMessage);
        } else if (tagsUpdateSuccess.isPresent()) {
            model.addAttribute("alertMessage", "Tags updated successfully.");
        }

        model.addAttribute("clients", userService.getAllClients());
        model.addAttribute("client", new ClientDto());
        model.addAttribute("tagCategories", tagCategoryService.getAllTagCategoriesAndTags());
        return "view-clients";
    }

    @PostMapping("/register")
    public String registerClient(@Valid @ModelAttribute("client") ClientDto clientDto,
                                 RedirectAttributes redirectAttributes,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clients", userService.getAllClients());
            model.addAttribute("dateOfBirth", clientDto.getDateOfBirth());
            model.addAttribute("registerValidationsFailed", true);
            return "view-clients";
        }
        userService.registerClient(clientDto);
        redirectAttributes.addFlashAttribute("flashAttribute",
                "Client registered successfully. A verification email has been sent to the client.");
        return "redirect:/clients?success";
    }

    @PostMapping("/{id}/update")
    public String updateClient(@PathVariable("id") int id,
                               @Valid @ModelAttribute("client") ClientDto clientDto,
                               RedirectAttributes redirectAttributes,
                               BindingResult result,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clients", userService.getAllClients());
            model.addAttribute("editedClient", clientDto);
            model.addAttribute("updateValidationsFailed", true);
            return "view-clients";
        }
        int userId = userService.getUserById(id).getId();
        userInfoService.updateUserInfo(clientDto, userId);
        redirectAttributes.addFlashAttribute("flashAttribute", "Client updated successfully.");
        return "redirect:/clients?success";
    }

    @GetMapping("/{id}/delete")
    public String disableClient(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        userService.disableUser(id);
        redirectAttributes.addFlashAttribute("flashAttribute", "Client deleted successfully.");
        return "redirect:/clients?success";
    }

}

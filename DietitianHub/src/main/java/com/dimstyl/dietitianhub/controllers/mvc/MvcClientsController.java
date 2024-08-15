package com.dimstyl.dietitianhub.controllers.mvc;

import com.dimstyl.dietitianhub.dtos.ClientDto;
import com.dimstyl.dietitianhub.dtos.DietPlanDto;
import com.dimstyl.dietitianhub.entities.User;
import com.dimstyl.dietitianhub.services.DietPlanService;
import com.dimstyl.dietitianhub.services.TagCategoryService;
import com.dimstyl.dietitianhub.services.UserInfoService;
import com.dimstyl.dietitianhub.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class MvcClientsController {

    private final UserService userService;
    private final UserInfoService userInfoService;
    private final TagCategoryService tagCategoryService;
    private final DietPlanService dietPlanService;

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
        return "clients";
    }

    @PostMapping("/register")
    public String registerClient(@Valid @ModelAttribute("client") ClientDto clientDto,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) throws MessagingException {
        if (result.hasErrors()) {
            model.addAttribute("clients", userService.getAllClients());
            model.addAttribute("dateOfBirth", clientDto.getDateOfBirth());
            model.addAttribute("registerValidationsFailed", true);
            return "clients";
        }
        userService.registerClient(clientDto);
        redirectAttributes.addFlashAttribute("flashAttribute", """
                The client has been registered successfully, \
                and an activation email has been sent to their inbox.""");
        return "redirect:/clients?success";
    }

    @PostMapping("/{id}/update")
    public String updateClient(@PathVariable("id") int id,
                               @Valid @ModelAttribute("client") ClientDto clientDto,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clients", userService.getAllClients());
            model.addAttribute("editedClient", clientDto);
            model.addAttribute("updateValidationsFailed", true);
            return "clients";
        }
        int userId = userService.getUser(id).getId();
        userInfoService.updateUserInfo(clientDto, userId);
        redirectAttributes.addFlashAttribute("flashAttribute", "Client updated successfully.");
        return "redirect:/clients?success";
    }

    @GetMapping("/{id}/delete")
    public String deleteClient(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("flashAttribute", "Client deleted successfully.");
        return "redirect:/clients?success";
    }

    @GetMapping("/{id}/dietPlans/upload")
    public String uploadDietPlanPage(@PathVariable("id") int id, Model model) {
        User user = userService.getUser(id);
        List<DietPlanDto> dietPlans = dietPlanService.getDietPlans(user.getUserInfo().getId());
        model.addAttribute("clientId", id);
        model.addAttribute("dietPlans", dietPlans);
        return "upload-diet-plan";
    }

    @PostMapping("/{id}/dietPlans/upload")
    public String uploadDietPlan(@PathVariable("id") int id, @RequestParam("file") MultipartFile file) {
        // Redirect with error message if no file is selected
        if (file.isEmpty()) {
            return "redirect:/clients/%d/dietPlans/upload?noFile".formatted(id);
        }

        // Redirect with error message if the file is not a PDF
        String fileContentType = file.getContentType();
        if (fileContentType == null || !fileContentType.equals("application/pdf")) {
            return "redirect:/clients/%d/dietPlans/upload?invalidFile".formatted(id);
        }

        // Save the diet plan file
        User user = userService.getUser(id);
        dietPlanService.saveDietPlan(user.getUserInfo(), file);

        // Redirect with success message
        return "redirect:/clients/%d/dietPlans/upload?uploadSuccess".formatted(id);
    }

    @GetMapping("/{clientId}/dietPlans/{dietPlanId}/download")
    public ResponseEntity<Resource> downloadDietPlan(@PathVariable("clientId") int clientId,
                                                     @PathVariable("dietPlanId") int dietPlanId) {
        User user = userService.getUser(clientId);
        Resource file = dietPlanService.getDietPlanFileAsResource(dietPlanId, user.getUserInfo());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }

    @GetMapping("/{clientId}/dietPlans/{dietPlanId}/view")
    public ResponseEntity<Resource> viewDietPlan(@PathVariable("clientId") int clientId,
                                                 @PathVariable("dietPlanId") int dietPlanId) {
        User user = userService.getUser(clientId);
        Resource file = dietPlanService.getDietPlanFileAsResource(dietPlanId, user.getUserInfo());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }

    @GetMapping("/{clientId}/dietPlans/{dietPlanId}/delete")
    public String deleteDietPlan(@PathVariable("clientId") int clientId,
                                 @PathVariable("dietPlanId") int dietPlanId) {
        User user = userService.getUser(clientId);
        dietPlanService.deleteDietPlan(dietPlanId, user.getUserInfo());
        return "redirect:/clients/%d/dietPlans/upload?deleteSuccess".formatted(clientId);
    }

}

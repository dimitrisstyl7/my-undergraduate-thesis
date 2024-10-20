package gr.unipi.thesis.dimstyl.controllers.web;

import gr.unipi.thesis.dimstyl.dtos.web.WebClientDto;
import gr.unipi.thesis.dimstyl.dtos.web.WebDietPlanDto;
import gr.unipi.thesis.dimstyl.dtos.web.WebTagDto;
import gr.unipi.thesis.dimstyl.entities.User;
import gr.unipi.thesis.dimstyl.entities.UserInfo;
import gr.unipi.thesis.dimstyl.enums.RequestType;
import gr.unipi.thesis.dimstyl.services.DietPlanService;
import gr.unipi.thesis.dimstyl.services.TagCategoryService;
import gr.unipi.thesis.dimstyl.services.UserInfoService;
import gr.unipi.thesis.dimstyl.services.UserService;
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

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class WebClientController {

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
        model.addAttribute("client", new WebClientDto());
        model.addAttribute("tagCategories", tagCategoryService.getAllTagCategoriesAndTags());
        model.addAttribute("maxDateOfBirth", LocalDate.now());

        return "clients";
    }

    @PostMapping("/register")
    public String registerClient(@Valid @ModelAttribute("client") WebClientDto webClientDto,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) throws MessagingException {
        if (result.hasErrors()) {
            model.addAttribute("clients", userService.getAllClients());
            model.addAttribute("dateOfBirth", webClientDto.getDateOfBirth());
            model.addAttribute("registerValidationsFailed", true);
            return "clients";
        }

        userService.registerClient(webClientDto);
        redirectAttributes.addFlashAttribute("flashAttribute", """
                The client has been registered successfully, \
                and an activation email has been sent to their inbox.""");

        return "redirect:/clients?success";
    }

    @PostMapping("/{id}/update")
    public String updateClient(@PathVariable("id") int id,
                               @Valid @ModelAttribute("client") WebClientDto webClientDto,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (result.hasErrors()) {
            model.addAttribute("clients", userService.getAllClients());
            model.addAttribute("editedClient", webClientDto);
            model.addAttribute("updateValidationsFailed", true);
            return "clients";
        }

        int userId = userService.getUser(id).getId();
        userInfoService.updateUserInfo(webClientDto, userId, RequestType.WEB);
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
        UserInfo userInfo = user.getUserInfo();
        List<WebDietPlanDto> dietPlans = dietPlanService.getDietPlans(userInfo.getId());
        model.addAttribute("clientId", id);
        model.addAttribute("clientFullName", userInfo.getFullName());
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
        Resource file = dietPlanService.getDietPlanFileAsResource(dietPlanId, user.getUserInfo(), RequestType.WEB_API);
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
        Resource file = dietPlanService.getDietPlanFileAsResource(dietPlanId, user.getUserInfo(), RequestType.WEB_API);
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

    @GetMapping("/{id}/tags")
    public ResponseEntity<List<WebTagDto>> getClientTags(@PathVariable("id") int id) {
        return ResponseEntity.ok(userInfoService.getClientTags(id, RequestType.WEB_API));
    }

    @PutMapping("/{id}/tags")
    public ResponseEntity<Void> updateClientTags(@PathVariable("id") int id, @RequestBody List<Integer> tagIds) {
        userInfoService.updateClientTags(id, tagIds, RequestType.WEB_API);
        return ResponseEntity.noContent().location(URI.create("/clients?tagsUpdateSuccess")).build();
    }

}

package gr.unipi.thesis.dimstyl.controllers.web;

import gr.unipi.thesis.dimstyl.dtos.web.WebClientCredentialChangeDto;
import gr.unipi.thesis.dimstyl.security.CustomUserDetailsService;
import gr.unipi.thesis.dimstyl.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
public class WebAuthController {

    private final UserService userService;
    private final CustomUserDetailsService userDetailsService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/updateCredentials")
    public String updateCredentialsPage(Model model) {
        model.addAttribute("client", new WebClientCredentialChangeDto());
        return "auth/update-credentials";
    }

    @PostMapping("/updateCredentials")
    public String updateCredentials(@Valid @ModelAttribute("client") WebClientCredentialChangeDto credentialChangeDto,
                                    BindingResult result,
                                    HttpServletRequest request,
                                    Model model) throws ServletException {
        if (result.hasErrors()) {
            model.addAttribute("client", credentialChangeDto);
            return "auth/update-credentials";
        }

        userService.updateClientCredentials(credentialChangeDto);
        userDetailsService.logout(request);

        return "redirect:/auth/updateCredentials/confirmation";
    }

    @GetMapping("/updateCredentials/confirmation")
    public String updateCredentialsSuccessPage() {
        return "auth/update-credentials-confirmation";
    }

}

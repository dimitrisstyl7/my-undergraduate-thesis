package com.dimstyl.dietitianhub.controllers.mvc;

import com.dimstyl.dietitianhub.dtos.ClientCredentialChangeDto;
import com.dimstyl.dietitianhub.security.CustomUserDetailsService;
import com.dimstyl.dietitianhub.services.UserService;
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
public class MvcAuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/updateCredentials")
    public String updateCredentialsPage(Model model) {
        model.addAttribute("client", new ClientCredentialChangeDto());
        return "auth/update-credentials";
    }

    @PostMapping("/updateCredentials")
    public String updateCredentials(@Valid @ModelAttribute("client") ClientCredentialChangeDto credentialChangeDto,
                                    HttpServletRequest request,
                                    BindingResult result,
                                    Model model) throws ServletException {
        if (result.hasErrors()) {
            model.addAttribute("client", credentialChangeDto);
            return "auth/update-credentials";
        }

        userService.updateClientCredentials(credentialChangeDto);
        CustomUserDetailsService.logout(request);

        return "redirect:/auth/updateCredentials/confirmation";
    }

    @GetMapping("/updateCredentials/confirmation")
    public String updateCredentialsSuccessPage() {
        return "auth/update-credentials-confirmation";
    }

}

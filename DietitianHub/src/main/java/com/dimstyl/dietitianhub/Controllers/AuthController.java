package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Endpoints;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping(Endpoints.LOGIN_ENDPOINT)
    public String loginPage() {
        return Endpoints.LOGIN_HTML;
    }
}

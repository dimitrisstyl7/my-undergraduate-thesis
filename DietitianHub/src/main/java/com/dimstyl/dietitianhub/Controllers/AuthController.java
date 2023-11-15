package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping(Constants.LOGIN_URL)
    public String loginPage() {
        return Constants.LOGIN_HTML;
    }
}

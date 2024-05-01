package com.dimstyl.dietitianhub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.dimstyl.dietitianhub.constants.Endpoints.LOGIN_ENDPOINT;
import static com.dimstyl.dietitianhub.constants.HtmlFiles.LOGIN_HTML;

@Controller
public class AuthController {
    @GetMapping(LOGIN_ENDPOINT)
    public String loginPage() {
        return LOGIN_HTML;
    }
}

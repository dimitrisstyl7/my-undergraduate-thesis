package com.dimstyl.dietitianhub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.dimstyl.dietitianhub.constants.Endpoints.MESSAGES_ENDPOINT;
import static com.dimstyl.dietitianhub.constants.HtmlFiles.MESSAGES_HTML;

@Controller
public class MessagesController {
    @GetMapping(MESSAGES_ENDPOINT)
    public String messagesPage() {
        return MESSAGES_HTML;
    }
}

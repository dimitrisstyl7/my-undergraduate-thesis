package com.dimstyl.dietitianhub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessagesController {
    @GetMapping("/messages")
    public String messagesPage() {
        return "messages";
    }
}

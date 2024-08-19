package com.dimstyl.dietitianhub.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcMessageController {

    @GetMapping("/messages")
    public String messagesPage() {
        return "messages";
    }

}

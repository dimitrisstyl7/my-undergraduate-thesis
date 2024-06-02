package com.dimstyl.dietitianhub.mvcControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcMessagesController {

    @GetMapping("/messages")
    public String messagesPage() {
        return "messages";
    }

}

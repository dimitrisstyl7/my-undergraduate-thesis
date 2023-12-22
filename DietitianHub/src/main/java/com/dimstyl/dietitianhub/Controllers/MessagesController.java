package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Endpoints;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessagesController {
    @GetMapping(Endpoints.MESSAGES_ENDPOINT)
    public String messagesPage() {
        return Endpoints.MESSAGES_HTML;
    }
}

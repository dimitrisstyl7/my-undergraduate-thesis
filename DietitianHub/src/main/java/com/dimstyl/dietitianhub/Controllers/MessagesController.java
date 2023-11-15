package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessagesController {
    @GetMapping(Constants.MESSAGES_URL)
    public String messagesPage() {
        return Constants.MESSAGES_HTML;
    }
}

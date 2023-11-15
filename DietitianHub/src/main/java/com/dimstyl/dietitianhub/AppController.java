package com.dimstyl.dietitianhub;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@NoArgsConstructor
public class AppController {
    @GetMapping({Constants.INDEX_URL})
    public String indexPage() {
        return Constants.INDEX_FILE;
    }

    @GetMapping(Constants.APPOINTMENTS_URL)
    public String appointmentsPage() {
        return Constants.APPOINTMENTS_FILE;
    }

    @GetMapping(Constants.MESSAGES_URL)
    public String chatPage() {
        return Constants.MESSAGES_FILE;
    }

    @GetMapping(Constants.VIEW_CLIENTS_URL)
    public String viewClientsPage() {
        return Constants.VIEW_CLIENTS_FILE;
    }

    @GetMapping(Constants.LOGIN_URL)
    public String loginPage() {
        return Constants.LOGIN_FILE;
    }
}
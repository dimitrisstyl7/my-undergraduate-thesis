package com.dimstyl.dietitianhub;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@NoArgsConstructor
public class AppController {
    @GetMapping({Constants.INDEX_URL_1, Constants.INDEX_URL_2})
    public String indexPage() {
        return Constants.INDEX_FILE;
    }

    @GetMapping(Constants.APPOINTMENTS_URL)
    public String appointmentsPage() {
        return Constants.APPOINTMENTS_FILE;
    }

    @GetMapping(Constants.CHAT_URL)
    public String chatPage() {
        return Constants.CHAT_FILE;
    }

    @GetMapping(Constants.REGISTER_CLIENT_URL)
    public String registerClientPage() {
        return Constants.REGISTER_CLIENT_FILE;
    }

    @GetMapping(Constants.VIEW_CLIENTS_URL)
    public String viewClientsPage() {
        return Constants.VIEW_CLIENTS_FILE;
    }

    @GetMapping(Constants.REMOVE_CLIENT_URL)
    public String removeClientPage() {
        return Constants.REMOVE_CLIENT_FILE;
    }

    @GetMapping(Constants.LOGIN_URL)
    public String loginPage() {
        return Constants.LOGIN_FILE;
    }
}
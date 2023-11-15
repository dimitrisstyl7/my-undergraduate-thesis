package com.dimstyl.dietitianhub;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@NoArgsConstructor
public class AppController {
    @GetMapping({Constants.INDEX_URL})
    public String indexPage() {
        return Constants.INDEX_HTML;
    }

    @GetMapping(Constants.APPOINTMENTS_URL)
    public String appointmentsPage() {
        return Constants.APPOINTMENTS_HTML;
    }

    @GetMapping(Constants.MESSAGES_URL)
    public String chatPage() {
        return Constants.MESSAGES_HTML;
    }

    @GetMapping(Constants.VIEW_CLIENTS_URL)
    public String viewClientsPage() {
        return Constants.VIEW_CLIENTS_HTML;
    }

    @GetMapping(Constants.LOGIN_URL)
    public String loginPage() {
        return Constants.LOGIN_HTML;
    }

    @GetMapping(Constants.UPLOAD_FILE_URL)
    public String uploadFilePage() {
        return Constants.UPLOAD_FILE_HTML;
    }

    @PostMapping(Constants.UPLOAD_FILE_URL)
    public String uploadFile() {
        System.out.println("Uploading file...");
        return Constants.INDEX_HTML;
    }
}
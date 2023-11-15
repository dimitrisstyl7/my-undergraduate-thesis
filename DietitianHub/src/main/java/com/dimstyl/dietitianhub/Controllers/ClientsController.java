package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientsController {
    @GetMapping(Constants.VIEW_CLIENTS_URL)
    public String viewClientsPage() {
        return Constants.VIEW_CLIENTS_HTML;
    }
}

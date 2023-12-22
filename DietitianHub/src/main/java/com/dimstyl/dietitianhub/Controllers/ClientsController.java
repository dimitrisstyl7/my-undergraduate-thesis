package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Endpoints;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientsController {
    @GetMapping(Endpoints.VIEW_CLIENTS_ENDPOINT)
    public String viewClientsPage() {
        return Endpoints.VIEW_CLIENTS_HTML;
    }
}

package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Endpoints;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnnouncementsController {
    @GetMapping(Endpoints.ANNOUNCEMENTS_ENDPOINT)
    public String announcementsPage() {
        return Endpoints.ANNOUNCEMENTS_HTML;
    }
}

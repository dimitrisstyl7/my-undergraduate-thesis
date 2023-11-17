package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnnouncementsController {
    @GetMapping(Constants.ANNOUNCEMENTS_URL)
    public String announcementsPage() {
        return Constants.ANNOUNCEMENTS_HTML;
    }
}

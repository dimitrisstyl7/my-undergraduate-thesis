package com.dimstyl.dietitianhub.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcAnnouncementsController {

    @GetMapping("/announcements")
    public String announcementsPage() {
        return "announcements";
    }

}

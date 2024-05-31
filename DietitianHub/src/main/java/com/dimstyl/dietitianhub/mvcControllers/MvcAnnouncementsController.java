package com.dimstyl.dietitianhub.mvcControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcAnnouncementsController {
    @GetMapping("/announcements")
    public String announcementsPage() {
        return "announcements";
    }
}

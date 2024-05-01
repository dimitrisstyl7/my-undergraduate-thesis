package com.dimstyl.dietitianhub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnnouncementsController {
    @GetMapping("/announcements")
    public String announcementsPage() {
        return "announcements";
    }
}

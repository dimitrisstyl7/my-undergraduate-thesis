package com.dimstyl.dietitianhub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.dimstyl.dietitianhub.constants.Endpoints.ANNOUNCEMENTS_ENDPOINT;
import static com.dimstyl.dietitianhub.constants.HtmlFileNames.ANNOUNCEMENTS_HTML;

@Controller
public class AnnouncementsController {
    @GetMapping(ANNOUNCEMENTS_ENDPOINT)
    public String announcementsPage() {
        return ANNOUNCEMENTS_HTML;
    }
}

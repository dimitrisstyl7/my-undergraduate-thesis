package com.dimstyl.dietitianhub.controllers.mvc;

import com.dimstyl.dietitianhub.dtos.AnnouncementDto;
import com.dimstyl.dietitianhub.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class MvcAnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    public String announcementsPage(Model model) {
        List<AnnouncementDto> announcementsForToday = announcementService.getAnnouncementsForToday();
        List<AnnouncementDto> announcementsForYesterday = announcementService.getAnnouncementsForYesterday();
        List<AnnouncementDto> earlierAnnouncements = announcementService.getEarlierAnnouncements();

        model.addAttribute("announcementsForToday", announcementsForToday);
        model.addAttribute("announcementsForYesterday", announcementsForYesterday);
        model.addAttribute("earlierAnnouncements", earlierAnnouncements);

        return "announcements";
    }

}

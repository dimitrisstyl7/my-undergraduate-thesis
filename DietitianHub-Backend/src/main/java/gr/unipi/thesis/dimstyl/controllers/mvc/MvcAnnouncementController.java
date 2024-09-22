package gr.unipi.thesis.dimstyl.controllers.mvc;

import gr.unipi.thesis.dimstyl.dtos.AnnouncementDto;
import gr.unipi.thesis.dimstyl.services.AnnouncementService;
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
        List<AnnouncementDto> earlierAnnouncements = announcementService.getLatest10AnnouncementsBeforeYesterday();
        boolean noAnnouncements = announcementsForToday.isEmpty() &&
                                  announcementsForYesterday.isEmpty() &&
                                  earlierAnnouncements.isEmpty();

        model.addAttribute("announcementsForToday", announcementsForToday);
        model.addAttribute("announcementsForYesterday", announcementsForYesterday);
        model.addAttribute("earlierAnnouncements", earlierAnnouncements);
        model.addAttribute("noAnnouncements", noAnnouncements);

        return "announcements";
    }

}

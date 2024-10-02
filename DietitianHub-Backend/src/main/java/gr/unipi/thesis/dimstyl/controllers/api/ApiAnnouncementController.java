package gr.unipi.thesis.dimstyl.controllers.api;

import gr.unipi.thesis.dimstyl.dtos.api.AnnouncementsResponse;
import gr.unipi.thesis.dimstyl.dtos.api.ApiAnnouncementDto;
import gr.unipi.thesis.dimstyl.entities.Announcement;
import gr.unipi.thesis.dimstyl.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/announcements")
@RequiredArgsConstructor
public class ApiAnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    public ResponseEntity<AnnouncementsResponse> getAnnouncements() {
        List<ApiAnnouncementDto> announcementsForToday = announcementService.getAnnouncementsForToday(Announcement::toApiDto);
        List<ApiAnnouncementDto> announcementsForYesterday = announcementService.getAnnouncementsForYesterday(Announcement::toApiDto);
        List<ApiAnnouncementDto> earlierAnnouncements = announcementService.getLatest10AnnouncementsBeforeYesterday(Announcement::toApiDto);

        AnnouncementsResponse announcementsResponse = AnnouncementsResponse.builder()
                .announcementsForToday(announcementsForToday)
                .announcementsForYesterday(announcementsForYesterday)
                .earlierAnnouncements(earlierAnnouncements)
                .build();

        return ResponseEntity.ok(announcementsResponse);
    }

}

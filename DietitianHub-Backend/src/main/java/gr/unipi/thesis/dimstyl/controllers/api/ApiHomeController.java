package gr.unipi.thesis.dimstyl.controllers.api;

import gr.unipi.thesis.dimstyl.dtos.api.ApiAnnouncementDto;
import gr.unipi.thesis.dimstyl.dtos.api.ApiAppointmentDto;
import gr.unipi.thesis.dimstyl.dtos.api.ApiArticleDto;
import gr.unipi.thesis.dimstyl.dtos.api.HomeResponse;
import gr.unipi.thesis.dimstyl.entities.Tag;
import gr.unipi.thesis.dimstyl.enums.AppointmentStatus;
import gr.unipi.thesis.dimstyl.security.CustomUserDetailsService;
import gr.unipi.thesis.dimstyl.services.AnnouncementService;
import gr.unipi.thesis.dimstyl.services.AppointmentService;
import gr.unipi.thesis.dimstyl.services.ArticleService;
import gr.unipi.thesis.dimstyl.services.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class ApiHomeController {

    private final CustomUserDetailsService userDetailsService;
    private final UserInfoService userInfoService;
    private final ArticleService articleService;
    private final AnnouncementService announcementService;
    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<HomeResponse> home() {
        String username = userDetailsService.getUserDetails().getUsername();
        String fullName = userDetailsService.getUserDetails().getFullName();
        List<Tag> tags = userInfoService.getClientTags(username);
        List<ApiArticleDto> articles = articleService.getLatest10ArticlesByTags(tags);
        List<ApiAnnouncementDto> announcements = announcementService.getLatest10Announcements();
        List<ApiAppointmentDto> appointments =
                appointmentService.getLatest5AppointmentsByClientUsernameAndStatus(username, AppointmentStatus.SCHEDULED);

        HomeResponse homeResponse = HomeResponse.builder()
                .fullName(fullName)
                .articles(articles)
                .announcements(announcements)
                .appointments(appointments)
                .build();

        return ResponseEntity.ok(homeResponse);
    }

}

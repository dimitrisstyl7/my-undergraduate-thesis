package gr.unipi.thesis.dimstyl.controllers.web;

import gr.unipi.thesis.dimstyl.dtos.web.WebAnnouncementDto;
import gr.unipi.thesis.dimstyl.services.AnnouncementService;
import gr.unipi.thesis.dimstyl.utilities.ValidationErrorUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class WebAnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    public String announcementsPage(Model model) {
        List<WebAnnouncementDto> announcementsForToday = announcementService.getAnnouncementsForToday();
        List<WebAnnouncementDto> announcementsForYesterday = announcementService.getAnnouncementsForYesterday();
        List<WebAnnouncementDto> earlierAnnouncements = announcementService.getLatest10AnnouncementsBeforeYesterday();
        boolean noAnnouncements = announcementsForToday.isEmpty() &&
                                  announcementsForYesterday.isEmpty() &&
                                  earlierAnnouncements.isEmpty();

        model.addAttribute("announcementsForToday", announcementsForToday);
        model.addAttribute("announcementsForYesterday", announcementsForYesterday);
        model.addAttribute("earlierAnnouncements", earlierAnnouncements);
        model.addAttribute("noAnnouncements", noAnnouncements);

        return "announcements";
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebAnnouncementDto> getAnnouncement(@PathVariable("id") int id) {
        return ResponseEntity.ok(announcementService.getAnnouncement(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createAnnouncement(@Valid @RequestBody WebAnnouncementDto webAnnouncementDto,
                                                                  BindingResult result) {
        // If there are no errors, proceed with creating the announcement
        if (!result.hasErrors()) {
            announcementService.createAnnouncement(webAnnouncementDto);
            return ResponseEntity.noContent().location(URI.create("/announcements?publishSuccess")).build();
        }

        // Otherwise, return a bad request
        Optional<String> titleError = ValidationErrorUtil.getTitleError(result);
        Optional<String> contentError = ValidationErrorUtil.getError("content", result);
        Map<String, String> errors = new HashMap<>() {
            {
                titleError.ifPresent(s -> put("title", s));
                contentError.ifPresent(s -> put("content", s));
            }
        };

        // If errors map is empty (no default message found), return a bad request without a body.
        // Otherwise, return a bad request with the errors map in the body.
        return errors.isEmpty() ? ResponseEntity.badRequest().build() : ResponseEntity.badRequest().body(errors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateAnnouncement(@PathVariable("id") int id,
                                                                  @Valid @RequestBody WebAnnouncementDto webAnnouncementDto,
                                                                  BindingResult result) {
        // If there are no errors, proceed with updating the announcement
        if (!result.hasErrors()) {
            announcementService.updateAnnouncement(id, webAnnouncementDto);
            return ResponseEntity.noContent().location(URI.create("/announcements?updateSuccess")).build();
        }

        // Otherwise, return a bad request
        Optional<String> titleError = ValidationErrorUtil.getTitleError(result);
        Optional<String> contentError = ValidationErrorUtil.getError("content", result);
        Map<String, String> errors = new HashMap<>() {
            {
                titleError.ifPresent(s -> put("title", s));
                contentError.ifPresent(s -> put("content", s));
            }
        };

        // If errors map is empty (no default message found), return a bad request without a body.
        // Otherwise, return a bad request with the errors map in the body.
        return errors.isEmpty() ? ResponseEntity.badRequest().build() : ResponseEntity.badRequest().body(errors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable("id") int id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.noContent().location(URI.create("/announcements?deleteSuccess")).build();
    }

}

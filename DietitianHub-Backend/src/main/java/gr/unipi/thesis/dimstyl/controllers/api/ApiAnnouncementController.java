package gr.unipi.thesis.dimstyl.controllers.api;

import gr.unipi.thesis.dimstyl.dtos.AnnouncementDto;
import gr.unipi.thesis.dimstyl.services.AnnouncementService;
import gr.unipi.thesis.dimstyl.utilities.ValidationErrorUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/announcements")
@RequiredArgsConstructor
public class ApiAnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDto> getAnnouncement(@PathVariable("id") int id) {
        return ResponseEntity.ok(announcementService.getAnnouncement(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createAnnouncement(@Valid @RequestBody AnnouncementDto announcementDto,
                                                                  BindingResult result) {
        // If there are no errors, proceed with creating the announcement.
        if (!result.hasErrors()) {
            announcementService.createAnnouncement(announcementDto);
            return ResponseEntity.noContent().location(URI.create("/announcements?publishSuccess")).build();
        }

        // Otherwise, return a bad request.
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
                                                                  @Valid @RequestBody AnnouncementDto announcementDto,
                                                                  BindingResult result) {
        // If there are no errors, proceed with updating the announcement.
        if (!result.hasErrors()) {
            announcementService.updateAnnouncement(id, announcementDto);
            return ResponseEntity.noContent().location(URI.create("/announcements?updateSuccess")).build();
        }

        // Otherwise, return a bad request.
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

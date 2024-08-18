package com.dimstyl.dietitianhub.controllers.api;

import com.dimstyl.dietitianhub.dtos.AnnouncementDto;
import com.dimstyl.dietitianhub.services.AnnouncementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
        // If there are no errors, continue with the creation of the announcement.
        if (!result.hasErrors()) {
            announcementService.createAnnouncement(announcementDto);
            return ResponseEntity.noContent().location(URI.create("/announcements?publishSuccess")).build();
        }

        // Otherwise, return a bad request.
        var titleError = result.getGlobalError();
        var contentError = result.getFieldError("content");
        Map<String, String> errors = new HashMap<>() {
            {
                if (titleError != null
                    && titleError.getDefaultMessage() != null) put("title", titleError.getDefaultMessage());

                if (contentError != null
                    && contentError.getDefaultMessage() != null) put("content", contentError.getDefaultMessage());
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
        // If there are no errors, continue with the update of the announcement.
        if (!result.hasErrors()) {
            announcementService.updateAnnouncement(id, announcementDto);
            return ResponseEntity.noContent().location(URI.create("/announcements?updateSuccess")).build();
        }

        // Otherwise, return a bad request.
        var titleError = result.getGlobalError();
        var contentError = result.getFieldError("content");
        Map<String, String> errors = new HashMap<>() {
            {
                if (titleError != null
                    && titleError.getDefaultMessage() != null) put("title", titleError.getDefaultMessage());

                if (contentError != null
                    && contentError.getDefaultMessage() != null) put("content", contentError.getDefaultMessage());
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

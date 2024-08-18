package com.dimstyl.dietitianhub.controllers.api;

import com.dimstyl.dietitianhub.dtos.AnnouncementDto;
import com.dimstyl.dietitianhub.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/announcements")
@RequiredArgsConstructor
public class ApiAnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDto> getAnnouncement(@PathVariable("id") int id) {
        return ResponseEntity.ok(announcementService.getAnnouncement(id));
    }

}

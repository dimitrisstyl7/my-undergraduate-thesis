package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.dtos.AnnouncementDto;
import com.dimstyl.dietitianhub.entities.Announcement;
import com.dimstyl.dietitianhub.exceptions.AnnouncementNotFoundException;
import com.dimstyl.dietitianhub.repositories.AnnouncementRepository;
import com.dimstyl.dietitianhub.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Override
    public List<AnnouncementDto> getAnnouncementsForToday() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        return announcementRepository.findAnnouncementByCreatedAtAfterOrderByCreatedAtDesc(startOfDay).stream()
                .map(Announcement::toDto)
                .toList();
    }

    @Override
    public List<AnnouncementDto> getAnnouncementsForYesterday() {
        LocalDateTime start = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().atStartOfDay().minusSeconds(1);
        return announcementRepository.findAnnouncementByCreatedAtBetweenOrderByCreatedAtDesc(start, end).stream()
                .map(Announcement::toDto)
                .toList();
    }

    @Override
    public List<AnnouncementDto> getEarlierAnnouncements() {
        LocalDateTime dateTime = LocalDate.now().minusDays(1).atStartOfDay().minusSeconds(1);
        return announcementRepository.findFirst10ByCreatedAtBeforeOrderByCreatedAtDesc(dateTime).stream()
                .map(Announcement::toDto)
                .toList();
    }

    @Override
    public AnnouncementDto getAnnouncement(int id) {
        return announcementRepository.findById(id)
                .map(Announcement::toDto)
                .orElseThrow(() -> new AnnouncementNotFoundException("Announcement with id %d not found".formatted(id)));
    }

}

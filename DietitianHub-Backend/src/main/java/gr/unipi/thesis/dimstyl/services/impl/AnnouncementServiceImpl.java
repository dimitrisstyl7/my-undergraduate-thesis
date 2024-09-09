package gr.unipi.thesis.dimstyl.services.impl;

import gr.unipi.thesis.dimstyl.dtos.AnnouncementDto;
import gr.unipi.thesis.dimstyl.entities.Announcement;
import gr.unipi.thesis.dimstyl.exceptions.announcement.AnnouncementNotFoundException;
import gr.unipi.thesis.dimstyl.repositories.AnnouncementRepository;
import gr.unipi.thesis.dimstyl.services.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return announcementRepository
                .findAnnouncementByCreatedAtAfterOrderByCreatedAtDesc(startOfDay).stream()
                .map(Announcement::toDto)
                .toList();
    }

    @Override
    public List<AnnouncementDto> getAnnouncementsForYesterday() {
        LocalDateTime start = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime end = LocalDate.now().atStartOfDay().minusSeconds(1);
        return announcementRepository
                .findAnnouncementByCreatedAtBetweenOrderByCreatedAtDesc(start, end).stream()
                .map(Announcement::toDto)
                .toList();
    }

    @Override
    public List<AnnouncementDto> getFirst10EarlierAnnouncements() {
        LocalDateTime dateTime = LocalDate.now().minusDays(1).atStartOfDay().minusSeconds(1);
        return announcementRepository
                .findFirst10ByCreatedAtBeforeOrderByCreatedAtDesc(dateTime).stream()
                .map(Announcement::toDto)
                .toList();
    }

    @Override
    public AnnouncementDto getAnnouncement(int id) {
        return announcementRepository.findById(id)
                .map(Announcement::toDto)
                .orElseThrow(() -> new AnnouncementNotFoundException("Announcement with id %d not found".formatted(id)));
    }

    @Override
    @Transactional
    public void createAnnouncement(AnnouncementDto announcementDto) {
        Announcement announcement = announcementDto.toAnnouncement();
        announcementRepository.save(announcement);
    }

    @Override
    @Transactional
    public void updateAnnouncement(int id, AnnouncementDto announcementDto) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new AnnouncementNotFoundException("Announcement with id %d not found".formatted(id)));
        announcement.setTitle(announcementDto.title());
        announcement.setContent(announcementDto.content());
    }

    @Override
    @Transactional
    public void deleteAnnouncement(int id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new AnnouncementNotFoundException("Announcement with id %d not found".formatted(id)));
        announcementRepository.delete(announcement);
    }

    @Override
    public boolean existsByTitle(String title) {
        return announcementRepository.existsByTitle(title);
    }

}

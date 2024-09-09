package gr.unipi.thesis.dimstyl.repositories;

import gr.unipi.thesis.dimstyl.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

    List<Announcement> findAnnouncementByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime dateTime);

    List<Announcement> findAnnouncementByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime start, LocalDateTime end);

    List<Announcement> findFirst10ByCreatedAtBeforeOrderByCreatedAtDesc(LocalDateTime dateTime);

    boolean existsByTitle(String title);

}

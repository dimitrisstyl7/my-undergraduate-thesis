package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.AnnouncementDto;

import java.util.List;

public interface AnnouncementService {

    List<AnnouncementDto> getAnnouncementsForToday();

    List<AnnouncementDto> getAnnouncementsForYesterday();

    List<AnnouncementDto> getFirst10EarlierAnnouncements();

    AnnouncementDto getAnnouncement(int id);

    void createAnnouncement(AnnouncementDto announcementDto);

    void updateAnnouncement(int id, AnnouncementDto announcementDto);

    void deleteAnnouncement(int id);

    boolean existsByTitle(String title);

}

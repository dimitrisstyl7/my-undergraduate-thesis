package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.web.WebAnnouncementDto;

import java.util.List;

public interface AnnouncementService {

    List<WebAnnouncementDto> getAnnouncementsForToday();

    List<WebAnnouncementDto> getAnnouncementsForYesterday();

    List<WebAnnouncementDto> getLatest10AnnouncementsBeforeYesterday();


    WebAnnouncementDto getAnnouncement(int id);

    void createAnnouncement(WebAnnouncementDto webAnnouncementDto);

    void updateAnnouncement(int id, WebAnnouncementDto webAnnouncementDto);

    void deleteAnnouncement(int id);

    boolean existsByTitle(String title);

}

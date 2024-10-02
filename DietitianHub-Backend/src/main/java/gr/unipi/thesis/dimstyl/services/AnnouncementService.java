package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.api.ApiAnnouncementDto;
import gr.unipi.thesis.dimstyl.dtos.web.WebAnnouncementDto;
import gr.unipi.thesis.dimstyl.entities.Announcement;

import java.util.List;
import java.util.function.Function;

public interface AnnouncementService {

    <T> List<T> getAnnouncementsForToday(Function<Announcement, T> mapper);

    <T> List<T> getAnnouncementsForYesterday(Function<Announcement, T> mapper);

    <T> List<T> getLatest10AnnouncementsBeforeYesterday(Function<Announcement, T> mapper);

    List<ApiAnnouncementDto> getLatest10Announcements();

    WebAnnouncementDto getAnnouncement(int id);

    void createAnnouncement(WebAnnouncementDto webAnnouncementDto);

    void updateAnnouncement(int id, WebAnnouncementDto webAnnouncementDto);

    void deleteAnnouncement(int id);

    boolean existsByTitle(String title);

}

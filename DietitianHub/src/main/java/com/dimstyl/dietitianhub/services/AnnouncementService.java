package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.AnnouncementDto;

import java.util.List;

public interface AnnouncementService {

    List<AnnouncementDto> getAnnouncementsForToday();

    List<AnnouncementDto> getAnnouncementsForYesterday();

    List<AnnouncementDto> getEarlierAnnouncements();

    AnnouncementDto getAnnouncement(int id);

    void updateAnnouncement(int id, AnnouncementDto announcementDto);

    boolean existsByTitle(String title);

}

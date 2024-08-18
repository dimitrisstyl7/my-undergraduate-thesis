package com.dimstyl.dietitianhub.dtos;

import com.dimstyl.dietitianhub.entities.Announcement;

public record AnnouncementDto(int id, String title, String content, String createdAt) {

    public Announcement toAnnouncement() {
        return Announcement.builder()
                .title(title)
                .content(content)
                .build();
    }

}


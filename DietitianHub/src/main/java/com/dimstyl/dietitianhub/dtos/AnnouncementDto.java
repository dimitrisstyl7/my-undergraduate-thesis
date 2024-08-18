package com.dimstyl.dietitianhub.dtos;

import com.dimstyl.dietitianhub.entities.Announcement;
import com.dimstyl.dietitianhub.validators.articleTitle.UniqueTitle;
import jakarta.validation.constraints.NotBlank;

@UniqueTitle
public record AnnouncementDto(int id,
                              @NotBlank(message = "Title cannot be empty") String title,
                              @NotBlank(message = "Content cannot be empty") String content,
                              String createdAt) implements IdentifiableAndTitleable {

    public Announcement toAnnouncement() {
        return Announcement.builder()
                .title(title)
                .content(content)
                .build();
    }

}

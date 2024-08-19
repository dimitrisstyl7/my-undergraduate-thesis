package com.dimstyl.dietitianhub.dtos;

import com.dimstyl.dietitianhub.entities.Announcement;
import com.dimstyl.dietitianhub.validators.articleTitle.UniqueTitle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@UniqueTitle
public record AnnouncementDto(int id,
                              @NotBlank(message = "Title cannot be empty")
                              @Size(max = 100, message = "Title must be less than 100 characters")
                              String title,
                              @NotBlank(message = "Content cannot be empty") String content,
                              String createdAt) implements IdentifiableAndTitleable {

    public Announcement toAnnouncement() {
        return Announcement.builder()
                .title(title)
                .content(content)
                .build();
    }

}

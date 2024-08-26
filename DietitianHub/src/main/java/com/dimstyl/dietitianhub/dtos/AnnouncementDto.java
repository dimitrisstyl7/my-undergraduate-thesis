package com.dimstyl.dietitianhub.dtos;

import com.dimstyl.dietitianhub.entities.Announcement;
import com.dimstyl.dietitianhub.validators.content.RichTextNotBlank;
import com.dimstyl.dietitianhub.validators.title.UniqueTitle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@UniqueTitle
public record AnnouncementDto(int id,
                              @NotBlank(message = "Title cannot be empty")
                              @Size(max = 100, message = "Title must be less than 100 characters")
                              String title,
                              @RichTextNotBlank String content,
                              String createdAt) implements IdentifiableAndTitleable {

    public Announcement toAnnouncement() {
        return Announcement.builder()
                .title(title)
                .content(content)
                .build();
    }

}

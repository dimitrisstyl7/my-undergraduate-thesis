package gr.unipi.thesis.dimstyl.dtos.web;

import gr.unipi.thesis.dimstyl.entities.Announcement;
import gr.unipi.thesis.dimstyl.validators.richText.RichTextNotBlank;
import gr.unipi.thesis.dimstyl.validators.title.UniqueTitle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@UniqueTitle
public record WebAnnouncementDto(int id,
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

package gr.unipi.thesis.dimstyl.dtos;

import gr.unipi.thesis.dimstyl.entities.Article;
import gr.unipi.thesis.dimstyl.entities.Tag;
import gr.unipi.thesis.dimstyl.validators.richText.RichTextNotBlank;
import gr.unipi.thesis.dimstyl.validators.title.UniqueTitle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
@UniqueTitle
public record ArticleDto(int id,
                         @NotBlank(message = "Title cannot be empty")
                         @Size(max = 100, message = "Title must be less than 100 characters")
                         String title,
                         @RichTextNotBlank String content,
                         List<Integer> tagIds,
                         List<String> tagNames,
                         String createdAt) implements IdentifiableAndTitleable {

    public Article toArticle(List<Tag> tags) {
        return Article.builder()
                .title(title)
                .content(content)
                .tags(tags)
                .build();
    }

}

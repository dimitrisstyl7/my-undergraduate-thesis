package com.dimstyl.dietitianhub.dtos;

import com.dimstyl.dietitianhub.entities.Article;
import com.dimstyl.dietitianhub.entities.Tag;
import com.dimstyl.dietitianhub.validators.richText.RichTextNotBlank;
import com.dimstyl.dietitianhub.validators.title.UniqueTitle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

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

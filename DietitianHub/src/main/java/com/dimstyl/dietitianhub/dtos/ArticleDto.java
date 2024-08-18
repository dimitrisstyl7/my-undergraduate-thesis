package com.dimstyl.dietitianhub.dtos;

import com.dimstyl.dietitianhub.entities.Article;
import com.dimstyl.dietitianhub.entities.Tag;
import com.dimstyl.dietitianhub.validators.articleTitle.UniqueTitle;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@UniqueTitle
public record ArticleDto(int id,
                         @NotBlank(message = "Title cannot be empty") String title,
                         @NotBlank(message = "Content cannot be empty") String content,
                         List<String> tagNames,
                         String createdAt,
                         List<Integer> tagIds) implements IdentifiableAndTitleable {

    public Article toArticle(List<Tag> tags) {
        return Article.builder()
                .title(title)
                .content(content)
                .tags(tags)
                .build();
    }

}

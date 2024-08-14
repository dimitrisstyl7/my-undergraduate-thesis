package com.dimstyl.dietitianhub.dtos;

import com.dimstyl.dietitianhub.entities.Article;
import com.dimstyl.dietitianhub.entities.Tag;
import com.dimstyl.dietitianhub.validators.articleTitle.Unique;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ArticleDto(int id,
                         @NotBlank(message = "Title cannot be empty") @Unique String title,
                         @NotBlank(message = "Content cannot be empty") String content,
                         List<String> tagNames,
                         String createdAt,
                         List<Integer> tagIds) {

    public Article toArticle(List<Tag> tags) {
        return Article.builder()
                .title(title)
                .content(content)
                .tags(tags)
                .build();
    }

}

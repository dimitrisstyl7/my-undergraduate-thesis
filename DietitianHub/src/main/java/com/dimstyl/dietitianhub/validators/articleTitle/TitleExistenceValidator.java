package com.dimstyl.dietitianhub.validators.articleTitle;

import com.dimstyl.dietitianhub.dtos.ArticleDto;
import com.dimstyl.dietitianhub.services.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TitleExistenceValidator implements ConstraintValidator<UniqueTitle, ArticleDto> {

    private final ArticleService articleService;
    private final HttpServletRequest request;

    @Override
    public void initialize(UniqueTitle constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ArticleDto articleDto, ConstraintValidatorContext constraintValidatorContext) {
        String newTitle = articleDto.title();

        // If the request method is POST (creating a new article), check if the title already exists.
        if (request.getMethod().equals("POST")) {
            return !articleService.existsByTitle(newTitle);
        }

        // If the request method is PUT (updating an existing article),
        // allow the old title to be the same as the new title.
        // Otherwise, check if the new title already exists.
        String oldTitle = articleService.getArticle(articleDto.id()).title();
        return oldTitle.equals(newTitle) || !articleService.existsByTitle(newTitle);
    }

}

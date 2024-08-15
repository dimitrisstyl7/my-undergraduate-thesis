package com.dimstyl.dietitianhub.validators.articleTitle;

import com.dimstyl.dietitianhub.dtos.ArticleDto;
import com.dimstyl.dietitianhub.services.ArticleService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TitleExistenceValidator implements ConstraintValidator<UniqueTitle, ArticleDto> {

    private final ArticleService articleService;

    @Override
    public void initialize(UniqueTitle constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ArticleDto articleDto, ConstraintValidatorContext constraintValidatorContext) {
        String oldTitle = articleService.getArticle(articleDto.id()).title();
        String newTitle = articleDto.title();
        return oldTitle.equals(newTitle) || !articleService.existsByTitle(newTitle);
    }

}

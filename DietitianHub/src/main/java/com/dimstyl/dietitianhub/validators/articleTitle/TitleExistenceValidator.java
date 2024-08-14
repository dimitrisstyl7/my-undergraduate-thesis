package com.dimstyl.dietitianhub.validators.articleTitle;

import com.dimstyl.dietitianhub.repositories.ArticleRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TitleExistenceValidator implements ConstraintValidator<Unique, String> {

    private final ArticleRepository articleRepository;

    @Override
    public void initialize(Unique constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !articleRepository.existsByTitle(s);
    }

}

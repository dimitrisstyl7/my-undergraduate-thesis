package com.dimstyl.dietitianhub.validators.richText;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.jsoup.Jsoup;

public class RichTextNotBlankValidator implements ConstraintValidator<RichTextNotBlank, String> {

    @Override
    public void initialize(RichTextNotBlank constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && !Jsoup.parse(s).text().isBlank();
    }

}

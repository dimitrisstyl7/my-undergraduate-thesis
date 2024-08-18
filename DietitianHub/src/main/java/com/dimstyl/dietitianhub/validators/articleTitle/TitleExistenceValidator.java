package com.dimstyl.dietitianhub.validators.articleTitle;

import com.dimstyl.dietitianhub.dtos.ArticleDto;
import com.dimstyl.dietitianhub.dtos.IdentifiableAndTitleable;
import com.dimstyl.dietitianhub.services.AnnouncementService;
import com.dimstyl.dietitianhub.services.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
public class TitleExistenceValidator implements ConstraintValidator<UniqueTitle, IdentifiableAndTitleable> {

    private final ArticleService articleService;
    private final AnnouncementService announcementService;
    private final HttpServletRequest request;

    @Override
    public void initialize(UniqueTitle constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(IdentifiableAndTitleable dto, ConstraintValidatorContext constraintValidatorContext) {
        // Determine if the dto is an ArticleDto.
        boolean isArticle = dto instanceof ArticleDto;

        // If the request method is POST (creating a new article/announcement), check if the title already exists.
        String httpMethod = request.getMethod();
        if (HttpMethod.valueOf(httpMethod) == HttpMethod.POST) {
            return doesNotExistByTitle(dto, isArticle);
        }

        // Otherwise, the request method is PUT (updating an existing article/announcement).
        // Allow the old title to be the same as the new title.
        // If old title != new title, check if the new title already exists.
        return oldTitleEqualsNewTitle(dto, isArticle) || doesNotExistByTitle(dto, isArticle);
    }

    private boolean doesNotExistByTitle(IdentifiableAndTitleable dto, boolean isArticle) {
        // Check if the title exists for the appropriate type of dto.
        return isArticle ? !articleService.existsByTitle(dto.title()) : !announcementService.existsByTitle(dto.title());
    }

    private boolean oldTitleEqualsNewTitle(IdentifiableAndTitleable x, boolean isArticle) {
        int id = x.id();
        String newTitle = x.title();

        // Compare the old title with the new title based on the type of the dto.
        return isArticle ?
                articleService.getArticle(id).title().equals(newTitle) :
                announcementService.getAnnouncement(id).title().equals(newTitle);
    }

}

package gr.unipi.thesis.dimstyl.controllers.api;

import gr.unipi.thesis.dimstyl.dtos.ArticleDto;
import gr.unipi.thesis.dimstyl.services.ArticleService;
import gr.unipi.thesis.dimstyl.utilities.ValidationErrorUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ApiArticleController {

    private final ArticleService articleService;

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticle(@PathVariable("id") int id) {
        return ResponseEntity.ok(articleService.getArticle(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createArticle(@Valid @RequestBody ArticleDto articleDto,
                                                             BindingResult result) {
        // If there are no errors, proceed with creating the article.
        if (!result.hasErrors()) {
            articleService.createArticle(articleDto);
            return ResponseEntity.noContent().location(URI.create("/articles?publishSuccess")).build();
        }

        // Otherwise, return a bad request.
        Optional<String> titleError = ValidationErrorUtil.getTitleError(result);
        Optional<String> contentError = ValidationErrorUtil.getError("content", result);
        Map<String, String> errors = new HashMap<>() {
            {
                titleError.ifPresent(s -> put("title", s));
                contentError.ifPresent(s -> put("content", s));
            }
        };

        // If errors map is empty (no default message found), return a bad request without a body.
        // Otherwise, return a bad request with the errors map in the body.
        return errors.isEmpty() ? ResponseEntity.badRequest().build() : ResponseEntity.badRequest().body(errors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateArticle(@PathVariable("id") int id,
                                                             @Valid @RequestBody ArticleDto articleDto,
                                                             BindingResult result) {
        // If there are no errors, proceed with updating the article.
        if (!result.hasErrors()) {
            articleService.updateArticle(id, articleDto);
            return ResponseEntity.noContent().location(URI.create("/articles?updateSuccess")).build();
        }

        // Otherwise, return a bad request.
        Optional<String> titleError = ValidationErrorUtil.getTitleError(result);
        Optional<String> contentError = ValidationErrorUtil.getError("content", result);
        Map<String, String> errors = new HashMap<>() {
            {
                titleError.ifPresent(s -> put("title", s));
                contentError.ifPresent(s -> put("content", s));
            }
        };

        // If errors map is empty (no default message found), return a bad request without a body.
        // Otherwise, return a bad request with the errors map in the body.
        return errors.isEmpty() ? ResponseEntity.badRequest().build() : ResponseEntity.badRequest().body(errors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") int id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().location(URI.create("/articles?deleteSuccess")).build();
    }

}

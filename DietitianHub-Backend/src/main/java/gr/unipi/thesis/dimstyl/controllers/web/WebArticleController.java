package gr.unipi.thesis.dimstyl.controllers.web;

import gr.unipi.thesis.dimstyl.dtos.web.WebArticleDto;
import gr.unipi.thesis.dimstyl.services.ArticleService;
import gr.unipi.thesis.dimstyl.services.TagCategoryService;
import gr.unipi.thesis.dimstyl.utilities.ValidationErrorUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class WebArticleController {

    private final TagCategoryService tagCategoryService;
    private final ArticleService articleService;

    @GetMapping
    public String articlesPage(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());
        model.addAttribute("tagCategories", tagCategoryService.getAllTagCategoriesAndTags());
        return "articles";
    }

    @GetMapping("/{id}")
    public ResponseEntity<WebArticleDto> getArticle(@PathVariable("id") int id) {
        return ResponseEntity.ok(articleService.getArticle(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createArticle(@Valid @RequestBody WebArticleDto webArticleDto,
                                                             BindingResult result) {
        // If there are no errors, proceed with creating the article
        if (!result.hasErrors()) {
            articleService.createArticle(webArticleDto);
            return ResponseEntity.noContent().location(URI.create("/articles?publishSuccess")).build();
        }

        // Otherwise, return a bad request
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
                                                             @Valid @RequestBody WebArticleDto webArticleDto,
                                                             BindingResult result) {
        // If there are no errors, proceed with updating the article
        if (!result.hasErrors()) {
            articleService.updateArticle(id, webArticleDto);
            return ResponseEntity.noContent().location(URI.create("/articles?updateSuccess")).build();
        }

        // Otherwise, return a bad request
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

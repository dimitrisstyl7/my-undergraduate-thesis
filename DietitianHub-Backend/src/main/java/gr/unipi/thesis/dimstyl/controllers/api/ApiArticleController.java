package gr.unipi.thesis.dimstyl.controllers.api;

import gr.unipi.thesis.dimstyl.dtos.api.ApiArticleDto;
import gr.unipi.thesis.dimstyl.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ApiArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<List<ApiArticleDto>> getArticles() {
        return ResponseEntity.ok(articleService.getLatest20Articles());
    }

}

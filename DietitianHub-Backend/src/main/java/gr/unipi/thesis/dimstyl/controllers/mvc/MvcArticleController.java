package gr.unipi.thesis.dimstyl.controllers.mvc;

import gr.unipi.thesis.dimstyl.services.ArticleService;
import gr.unipi.thesis.dimstyl.services.TagCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class MvcArticleController {

    private final TagCategoryService tagCategoryService;
    private final ArticleService articleService;

    @GetMapping
    public String articlesPage(Model model) {
        model.addAttribute("articles", articleService.getAllArticles());
        model.addAttribute("tagCategories", tagCategoryService.getAllTagCategoriesAndTags());
        return "articles";
    }

}

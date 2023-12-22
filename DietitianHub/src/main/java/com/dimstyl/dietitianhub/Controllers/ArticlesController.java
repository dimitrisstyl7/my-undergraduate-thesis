package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Endpoints;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticlesController {
    @GetMapping(Endpoints.ARTICLES_ENDPOINT)
    public String articlesPage() {
        return Endpoints.ARTICLES_HTML;
    }
}

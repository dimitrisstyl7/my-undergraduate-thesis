package com.dimstyl.dietitianhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.dimstyl.dietitianhub.constants.Endpoints.ARTICLES_ENDPOINT;
import static com.dimstyl.dietitianhub.constants.HtmlFileNames.ARTICLES_HTML;

@Controller
public class ArticlesController {
    @GetMapping(ARTICLES_ENDPOINT)
    public String articlesPage() {
        return ARTICLES_HTML;
    }
}

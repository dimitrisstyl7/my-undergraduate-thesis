package com.dimstyl.dietitianhub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.dimstyl.dietitianhub.constants.Endpoints.ARTICLES_ENDPOINT;
import static com.dimstyl.dietitianhub.constants.HtmlFiles.ARTICLES_HTML;

@Controller
public class ArticlesController {
    @GetMapping(ARTICLES_ENDPOINT)
    public String articlesPage() {
        return ARTICLES_HTML;
    }
}

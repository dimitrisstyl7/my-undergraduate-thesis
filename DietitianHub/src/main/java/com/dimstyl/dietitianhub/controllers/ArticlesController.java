package com.dimstyl.dietitianhub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticlesController {
    @GetMapping("/articles")
    public String articlesPage() {
        return "articles";
    }
}

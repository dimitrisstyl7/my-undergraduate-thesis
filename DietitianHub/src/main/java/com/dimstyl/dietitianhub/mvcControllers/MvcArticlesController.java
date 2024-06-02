package com.dimstyl.dietitianhub.mvcControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcArticlesController {

    @GetMapping("/articles")
    public String articlesPage() {
        return "articles";
    }

}

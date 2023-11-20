package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticlesController {
    @GetMapping(Constants.ARTICLES_URL)
    public String articlesPage() {
        return "articles";
    }
}

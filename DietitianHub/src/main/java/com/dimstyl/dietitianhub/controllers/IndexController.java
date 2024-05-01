package com.dimstyl.dietitianhub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.dimstyl.dietitianhub.constants.Endpoints.INDEX_ENDPOINT;
import static com.dimstyl.dietitianhub.constants.HtmlFiles.INDEX_HTML;

@Controller
public class IndexController {
    @GetMapping({INDEX_ENDPOINT})
    public String indexPage() {
        return INDEX_HTML;
    }
}
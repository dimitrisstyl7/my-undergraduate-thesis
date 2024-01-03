package com.dimstyl.dietitianhub.Controllers;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.dimstyl.dietitianhub.constants.Endpoints.INDEX_ENDPOINT;
import static com.dimstyl.dietitianhub.constants.HtmlFileNames.INDEX_HTML;

@Controller
@NoArgsConstructor
public class MainController {
    @GetMapping({INDEX_ENDPOINT})
    public String indexPage() {
        return INDEX_HTML;
    }
}
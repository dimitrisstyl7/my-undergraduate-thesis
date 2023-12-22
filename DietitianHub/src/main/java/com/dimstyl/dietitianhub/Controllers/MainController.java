package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Endpoints;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@NoArgsConstructor
public class MainController {
    @GetMapping({Endpoints.INDEX_ENDPOINT})
    public String indexPage() {
        return Endpoints.INDEX_HTML;
    }
}
package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Constants;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@NoArgsConstructor
public class MainController {
    @GetMapping({Constants.INDEX_URL})
    public String indexPage() {
        return Constants.INDEX_HTML;
    }
}
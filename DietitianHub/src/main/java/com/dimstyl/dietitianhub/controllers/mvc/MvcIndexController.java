package com.dimstyl.dietitianhub.controllers.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MvcIndexController {

    @GetMapping({"/", "/index"})
    public String indexPage() {
        return "index";
    }

}
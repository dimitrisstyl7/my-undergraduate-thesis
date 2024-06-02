package com.dimstyl.dietitianhub.mvcControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcIndexController {

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

}
package com.dimstyl.dietitianhub.mvcControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class MvcErrorController {

    @GetMapping("/userNotFound")
    public String UserNotFoundPage() {
        return "error/user-not-found";
    }

    @GetMapping("/tagsMismatch")
    public String TagMismatchPage() {
        return "error/tags-mismatch";
    }

    @GetMapping("/tagsNotFound")
    public String TagsNotFoundPage() {
        return "error/tags-not-found";
    }

    @GetMapping("/400")
    public String BadRequestPage() {
        return "error/400";
    }

}

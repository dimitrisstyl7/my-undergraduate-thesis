package com.dimstyl.dietitianhub;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@NoArgsConstructor
public class AppController {
    @GetMapping({Constants.INDEX_URL_1, Constants.INDEX_URL_2})
    public String indexPage() {
        return Constants.INDEX_FILE;
    }

    @GetMapping(Constants.NEW_PAGE_URL)
    public String newPage() {
        return Constants.NEW_PAGE_FILE;
    }

    @GetMapping(Constants.APPOINTMENTS_URL)
    public String appointments() {
        return Constants.APPOINTMENTS_FILE;
    }
}
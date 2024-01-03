package com.dimstyl.dietitianhub.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static com.dimstyl.dietitianhub.constants.Endpoints.UPLOAD_FILE_ENDPOINT;
import static com.dimstyl.dietitianhub.constants.HtmlFileNames.UPLOAD_FILE_HTML;

@Controller
public class FileUploaderController {
    @GetMapping(UPLOAD_FILE_ENDPOINT)
    public String uploadFilePage() {
        return UPLOAD_FILE_HTML;
    }

    @PostMapping(UPLOAD_FILE_ENDPOINT)
    public void uploadFile() {
        System.out.println("Uploading file...");
    }
}

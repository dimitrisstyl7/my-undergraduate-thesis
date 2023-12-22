package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Endpoints;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FileUploaderController {
    @GetMapping(Endpoints.UPLOAD_FILE_ENDPOINT)
    public String uploadFilePage() {
        return Endpoints.UPLOAD_FILE_HTML;
    }

    @PostMapping(Endpoints.UPLOAD_FILE_ENDPOINT)
    public void uploadFile() {
        System.out.println("Uploading file...");
    }
}

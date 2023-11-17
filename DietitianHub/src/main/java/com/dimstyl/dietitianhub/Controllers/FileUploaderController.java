package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FileUploaderController {
    @GetMapping(Constants.UPLOAD_FILE_URL)
    public String uploadFilePage() {
        return Constants.UPLOAD_FILE_HTML;
    }

    @PostMapping(Constants.UPLOAD_FILE_URL)
    public void uploadFile() {
        System.out.println("Uploading file...");
    }
}

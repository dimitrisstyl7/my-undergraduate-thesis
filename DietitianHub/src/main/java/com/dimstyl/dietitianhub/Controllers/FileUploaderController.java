package com.dimstyl.dietitianhub.Controllers;

import com.dimstyl.dietitianhub.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FileUploaderController {
    @PostMapping(Constants.UPLOAD_FILE_URL)
    public String uploadFile() {
        System.out.println("Uploading file...");
        return Constants.INDEX_HTML;
    }
}

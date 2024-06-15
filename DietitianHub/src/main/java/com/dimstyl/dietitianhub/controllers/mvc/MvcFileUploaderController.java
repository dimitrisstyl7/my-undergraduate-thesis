package com.dimstyl.dietitianhub.controllers.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MvcFileUploaderController {

    @GetMapping("/upload-file")
    public String uploadFilePage() {
        return "upload-file";
    }

    @PostMapping("/upload-file")
    public void uploadFile() {
        System.out.println("Uploading file...");
    }

}

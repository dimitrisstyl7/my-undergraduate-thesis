package com.dimstyl.dietitianhub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FileUploaderController {
    @GetMapping("/upload-file")
    public String uploadFilePage() {
        return "upload-file";
    }

    @PostMapping("/upload-file")
    public void uploadFile() {
        System.out.println("Uploading file...");
    }
}

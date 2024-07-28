package com.dimstyl.dietitianhub.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void storeDietPlanFile(String fileName, MultipartFile file);

    Resource loadDietPlanFileAsResource(String fileName);

    void deleteDietPlanFile(String fileName);

}

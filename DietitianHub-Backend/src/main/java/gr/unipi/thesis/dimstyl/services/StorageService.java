package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.enums.RequestType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void storeDietPlanFile(String fileName, MultipartFile file);

    Resource loadDietPlanFileAsResource(String fileName, RequestType requestType);

    void deleteDietPlanFile(String fileName);

}

package gr.unipi.thesis.dimstyl.services.impl;

import gr.unipi.thesis.dimstyl.dtos.api.ApiDietPlanDto;
import gr.unipi.thesis.dimstyl.dtos.web.WebDietPlanDto;
import gr.unipi.thesis.dimstyl.entities.DietPlan;
import gr.unipi.thesis.dimstyl.entities.UserInfo;
import gr.unipi.thesis.dimstyl.enums.RequestType;
import gr.unipi.thesis.dimstyl.exceptions.dietPlan.ApiDietPlanNotFoundException;
import gr.unipi.thesis.dimstyl.exceptions.dietPlan.WebDietPlanNotFoundException;
import gr.unipi.thesis.dimstyl.repositories.DietPlanRepository;
import gr.unipi.thesis.dimstyl.services.DietPlanService;
import gr.unipi.thesis.dimstyl.services.StorageService;
import gr.unipi.thesis.dimstyl.utilities.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DietPlanServiceImpl implements DietPlanService {

    private final DietPlanRepository dietPlanRepository;
    private final StorageService storageService;

    @Override
    @Transactional
    public void saveDietPlan(UserInfo userInfo, MultipartFile file) {
        // Generate a file name for the diet plan file
        String fileName = FileUtil.generateFileName(userInfo.getId());

        // Store the file in the file system
        storageService.storeDietPlanFile(fileName, file);

        // Save the file name in the database if not already exists
        if (!dietPlanRepository.existsByUserInfo_IdAndCreatedOn(userInfo.getId(), LocalDate.now())) {
            dietPlanRepository.save(DietPlan.builder()
                    .userInfo(userInfo)
                    .name(fileName)
                    .build()
            );
        }
    }

    @Override
    public DietPlan getDietPlan(int userInfoId, int dietPlanId) {
        return dietPlanRepository
                .findByIdAndUserInfo_Id(dietPlanId, userInfoId)
                .orElseThrow(() -> new WebDietPlanNotFoundException(
                        "Diet plan with id %d and user info id %d not found".formatted(dietPlanId, userInfoId))
                );
    }

    @Override
    public List<WebDietPlanDto> getDietPlans(int userInfoId) {
        return dietPlanRepository
                .findAllByUserInfo_IdOrderByCreatedOnDesc(userInfoId).stream()
                .map(DietPlan::toWebDto)
                .toList();
    }

    @Override
    public List<ApiDietPlanDto> getLatest15DietPlans(int userInfoId) {
        return dietPlanRepository.findFirst15ByUserInfo_IdOrderByCreatedOnDesc(userInfoId).stream()
                .map(DietPlan::toApiDto)
                .toList();
    }

    @Override
    public Resource getDietPlanFileAsResource(int dietPlanId, UserInfo userInfo, RequestType requestType) {
        // Get the diet plan by the user info id and diet plan id
        DietPlan dietPlan = getDietPlan(userInfo.getId(), dietPlanId);

        // Load and return the diet plan file as a resource
        return storageService.loadDietPlanFileAsResource(dietPlan.getName(), requestType);
    }

    @Override
    public Resource getDietPlanFileAsResource(int id, RequestType requestType) {
        DietPlan dietPlan = dietPlanRepository.findById(id).orElseThrow(
                () -> new ApiDietPlanNotFoundException("Diet plan with id %d not found".formatted(id))
        );

        // Load and return the diet plan file as a resource
        return storageService.loadDietPlanFileAsResource(dietPlan.getName(), requestType);
    }

    @Override
    @Transactional
    public void deleteDietPlan(int dietPlanId, UserInfo userInfo) {
        // Get the diet plan by the user info id and diet plan id
        DietPlan dietPlan = getDietPlan(userInfo.getId(), dietPlanId);
        String fileName = dietPlan.getName();

        // Delete the diet plan file from the file system
        storageService.deleteDietPlanFile(fileName);

        // Delete the diet plan from the database
        dietPlanRepository.delete(dietPlan);
    }

}

package gr.unipi.thesis.dimstyl.services.impl;

import gr.unipi.thesis.dimstyl.dtos.DietPlanDto;
import gr.unipi.thesis.dimstyl.entities.DietPlan;
import gr.unipi.thesis.dimstyl.entities.UserInfo;
import gr.unipi.thesis.dimstyl.exceptions.dietPlan.DietPlanNotFoundException;
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
                .orElseThrow(() -> new DietPlanNotFoundException(
                        "Diet plan with id %d and user info id %d not found".formatted(dietPlanId, userInfoId))
                );
    }

    @Override
    public List<DietPlanDto> getDietPlans(int userInfoId) {
        return dietPlanRepository
                .findAllByUserInfo_IdOrderByCreatedOnDesc(userInfoId).stream()
                .map(DietPlan::toDto)
                .toList();
    }

    @Override
    public Resource getDietPlanFileAsResource(int dietPlanId, UserInfo userInfo) {
        // Get the diet plan by the user info id and diet plan id
        DietPlan dietPlan = getDietPlan(userInfo.getId(), dietPlanId);

        // Load and return the diet plan file as a resource
        return storageService.loadDietPlanFileAsResource(dietPlan.getName());
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
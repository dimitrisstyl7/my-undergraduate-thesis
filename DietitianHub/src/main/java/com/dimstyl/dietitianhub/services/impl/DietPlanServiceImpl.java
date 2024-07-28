package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.dtos.DietPlanDto;
import com.dimstyl.dietitianhub.entities.DietPlan;
import com.dimstyl.dietitianhub.entities.UserInfo;
import com.dimstyl.dietitianhub.enums.RequestType;
import com.dimstyl.dietitianhub.exceptions.api.ApiDietPlanNotFoundException;
import com.dimstyl.dietitianhub.exceptions.mvc.MvcDietPlanNotFoundException;
import com.dimstyl.dietitianhub.mappers.DietPlanMapper;
import com.dimstyl.dietitianhub.repositories.DietPlanRepository;
import com.dimstyl.dietitianhub.services.DietPlanService;
import com.dimstyl.dietitianhub.services.StorageService;
import com.dimstyl.dietitianhub.services.UserInfoService;
import com.dimstyl.dietitianhub.utilities.FileUtil;
import com.dimstyl.dietitianhub.utilities.RequestTypeUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DietPlanServiceImpl implements DietPlanService {

    private final DietPlanRepository dietPlanRepository;
    private final UserInfoService userInfoService;
    private final StorageService storageService;
    private final HttpServletRequest request;

    @Override
    public void saveDietPlan(int clientId, MultipartFile file) {
        // Get the user info by the client id
        UserInfo userInfo = userInfoService.getUserInfoByUserId(clientId);

        // Generate a file name for the diet plan file
        String fileName = FileUtil.generateFileName(userInfo.getId());

        // Store the file in the file system
        storageService.storeDietPlanFile(fileName, file);

        // Save the file name in the database if not already exists
        if (!dietPlanRepository.existsByUserInfo_IdAndCreatedOn(userInfo.getId(), LocalDate.now())) {
            dietPlanRepository.save(DietPlan.builder()
                    .userInfo(userInfo)
                    .title(fileName)
                    .build()
            );
        }
    }

    @Override
    public DietPlan getDietPlan(int userInfoId, int dietPlanId) {
        RequestType requestType = RequestTypeUtil.getRequestType(request.getRequestURI());

        return dietPlanRepository
                .findByIdAndUserInfo_Id(dietPlanId, userInfoId)
                .orElseThrow(() -> {
                    String message = "Diet plan with id %d and user info id %d not found".formatted(dietPlanId, userInfoId);
                    if (requestType.equals(RequestType.API)) {
                        return new ApiDietPlanNotFoundException(message);
                    } else {
                        return new MvcDietPlanNotFoundException(message);
                    }
                });
    }

    @Override
    public List<DietPlanDto> getDietPlansByUserInfoId(int userInfoId) {
        return dietPlanRepository
                .findAllByUserInfo_IdOrderByCreatedOnDesc(userInfoId)
                .stream()
                .map(DietPlanMapper::mapToDietPlanDto)
                .toList();
    }

    @Override
    public Resource getDietPlanFileAsResource(int dietPlanId, int clientId) {
        // Get the user info by the client id
        UserInfo userInfo = userInfoService.getUserInfoByUserId(clientId);

        // Get the diet plan by the user info id and diet plan id
        DietPlan dietPlan = getDietPlan(userInfo.getId(), dietPlanId);

        // Load and return the diet plan file as a resource
        return storageService.loadDietPlanFileAsResource(dietPlan.getTitle());
    }

    @Override
    public void deleteDietPlan(int dietPlanId, int clientId) {
        // Get the user info by the client id
        UserInfo userInfo = userInfoService.getUserInfoByUserId(clientId);

        // Get the diet plan by the user info id and diet plan id
        DietPlan dietPlan = getDietPlan(userInfo.getId(), dietPlanId);
        String fileName = dietPlan.getTitle();

        // Delete the diet plan file from the file system
        storageService.deleteDietPlanFile(fileName);

        // Delete the diet plan from the database
        dietPlanRepository.delete(dietPlan);
    }

}
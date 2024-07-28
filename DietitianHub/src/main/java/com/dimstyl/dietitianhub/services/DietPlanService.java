package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.DietPlanDto;
import com.dimstyl.dietitianhub.entities.DietPlan;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DietPlanService {

    void saveDietPlan(int clientId, MultipartFile file);

    DietPlan getDietPlan(int userInfoId, int dietPlanId);

    List<DietPlanDto> getDietPlansByUserInfoId(int userInfoId);

    Resource getDietPlanFileAsResource(int dietPlanId, int clientId);

    void deleteDietPlan(int dietPlanId, int clientId);

}

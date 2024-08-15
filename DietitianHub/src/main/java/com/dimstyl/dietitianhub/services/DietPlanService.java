package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.DietPlanDto;
import com.dimstyl.dietitianhub.entities.DietPlan;
import com.dimstyl.dietitianhub.entities.UserInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DietPlanService {

    void saveDietPlan(UserInfo userInfo, MultipartFile file);

    DietPlan getDietPlan(int userInfoId, int dietPlanId);

    List<DietPlanDto> getDietPlans(int userInfoId);

    Resource getDietPlanFileAsResource(int dietPlanId, UserInfo userInfo);

    void deleteDietPlan(int dietPlanId, UserInfo userInfo);

}

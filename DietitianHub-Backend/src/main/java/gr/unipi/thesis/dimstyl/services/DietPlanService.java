package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.web.WebDietPlanDto;
import gr.unipi.thesis.dimstyl.entities.DietPlan;
import gr.unipi.thesis.dimstyl.entities.UserInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DietPlanService {

    void saveDietPlan(UserInfo userInfo, MultipartFile file);

    DietPlan getDietPlan(int userInfoId, int dietPlanId);

    List<WebDietPlanDto> getDietPlans(int userInfoId);

    Resource getDietPlanFileAsResource(int dietPlanId, UserInfo userInfo);

    void deleteDietPlan(int dietPlanId, UserInfo userInfo);

}

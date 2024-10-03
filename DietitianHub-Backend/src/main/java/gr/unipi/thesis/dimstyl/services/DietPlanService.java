package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.api.ApiDietPlanDto;
import gr.unipi.thesis.dimstyl.dtos.web.WebDietPlanDto;
import gr.unipi.thesis.dimstyl.entities.DietPlan;
import gr.unipi.thesis.dimstyl.entities.UserInfo;
import gr.unipi.thesis.dimstyl.enums.RequestType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DietPlanService {

    void saveDietPlan(UserInfo userInfo, MultipartFile file);

    DietPlan getDietPlan(int userInfoId, int dietPlanId);

    List<WebDietPlanDto> getDietPlans(int userInfoId);

    List<ApiDietPlanDto> getLatest15DietPlans(int userInfoId);

    Resource getDietPlanFileAsResource(int dietPlanId, UserInfo userInfo, RequestType requestType);

    Resource getDietPlanFileAsResource(int id, RequestType requestType);

    void deleteDietPlan(int dietPlanId, UserInfo userInfo);

}

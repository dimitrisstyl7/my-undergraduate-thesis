package com.dimstyl.dietitianhub.mappers;

import com.dimstyl.dietitianhub.dtos.DietPlanDto;
import com.dimstyl.dietitianhub.entities.DietPlan;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class DietPlanMapper {

    public static DietPlanDto mapToDietPlanDto(DietPlan dietPlan) {
        return new DietPlanDto(dietPlan.getId(), dietPlan.getName(), dietPlan.getCreatedOn());
    }

}

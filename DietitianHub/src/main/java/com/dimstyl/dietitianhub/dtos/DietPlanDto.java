package com.dimstyl.dietitianhub.dtos;

import com.dimstyl.dietitianhub.enums.Month;

import java.time.LocalDate;

public record DietPlanDto(int id, String name, LocalDate createdOn) {

    public String getFormattedDate() {
        // Return the date in the format "day month year"
        String day = String.valueOf(createdOn.getDayOfMonth());
        String month = Month.byNumber(createdOn.getMonthValue());
        String year = String.valueOf(createdOn.getYear());
        return "%s %s %s".formatted(day, month, year);
    }

}

package com.dimstyl.dietitianhub.utilities;

import com.dimstyl.dietitianhub.enums.Month;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public final class DateUtil {

    public static String getFormattedDate(LocalDate date) {
        // Return the date in the format "day month year"
        String day = String.valueOf(date.getDayOfMonth());
        String month = Month.byNumber(date.getMonthValue());
        String year = String.valueOf(date.getYear());
        return "%s %s %s".formatted(day, month, year);
    }

    public static String getFormattedDate(LocalDateTime dateTime) {
        // Return the date in the format "day month year"
        String day = String.valueOf(dateTime.getDayOfMonth());
        String month = Month.byNumber(dateTime.getMonthValue());
        String year = String.valueOf(dateTime.getYear());
        return "%s %s %s".formatted(day, month, year);
    }

}

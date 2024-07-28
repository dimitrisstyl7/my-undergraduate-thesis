package com.dimstyl.dietitianhub.utilities;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public final class FileUtil {

    public static String generateFileName(int userInfoId) {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();

        // File name format: {userInfoId}-{year}-{month}-{day}.pdf
        return "%d-%d-%d-%d.pdf".formatted(userInfoId, year, month, day);
    }

}

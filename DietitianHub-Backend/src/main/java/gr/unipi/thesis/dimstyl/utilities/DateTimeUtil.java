package gr.unipi.thesis.dimstyl.utilities;

import gr.unipi.thesis.dimstyl.enums.Month;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public final class DateTimeUtil {

    public static String getFormattedDate(LocalDate date) {
        // Return the date in the format "day month year"
        String day = String.valueOf(date.getDayOfMonth());
        String month = Month.byNumber(date.getMonthValue());
        String year = String.valueOf(date.getYear());
        return "%s %s %s".formatted(day, month, year);
    }

    public static String getFormattedDateTime(LocalDateTime dateTime) {
        // Return the date and time in the format "day month year, hh:mm AM/PM"
        return "%s, %s".formatted(getFormattedDate(dateTime), getFormattedTime(dateTime));
    }

    private static String getFormattedDate(LocalDateTime dateTime) {
        return getFormattedDate(dateTime.toLocalDate());
    }

    private static String getFormattedTime(LocalDateTime dateTime) {
        // Return the time in the format "hh:mm AM/PM"
        return dateTime.format(DateTimeFormatter.ofPattern("hh:mm a", java.util.Locale.ENGLISH));
    }

}

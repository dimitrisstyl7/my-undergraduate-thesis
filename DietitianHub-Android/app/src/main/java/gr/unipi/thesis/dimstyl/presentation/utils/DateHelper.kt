package gr.unipi.thesis.dimstyl.presentation.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.LocalDate
import java.time.Year
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
object PastSelectableDates : SelectableDates {

    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis < System.currentTimeMillis()
    }

}

@OptIn(ExperimentalMaterial3Api::class)
object FutureSelectableDates : SelectableDates {

    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= System.currentTimeMillis()
    }

}

fun yearRange(inPast: Boolean): IntRange {
    return if (inPast) 1930..getCurrentYear()
    else getCurrentYear()..getCurrentYear() + 1
}

fun getDateMillis(date: String): Long {
    return LocalDate.parse(date).atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant()
        .toEpochMilli()
}

private fun getCurrentYear(): Int = Year.now().value
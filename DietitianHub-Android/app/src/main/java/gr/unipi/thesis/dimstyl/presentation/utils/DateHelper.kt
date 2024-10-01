package gr.unipi.thesis.dimstyl.presentation.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.text.SimpleDateFormat
import java.time.Year
import java.util.Date
import java.util.Locale

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

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

private fun getCurrentYear(): Int = Year.now().value
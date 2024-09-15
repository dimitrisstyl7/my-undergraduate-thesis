package gr.unipi.thesis.dimstyl.helpers

import android.os.Build
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.text.SimpleDateFormat
import java.time.Year
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
object PastSelectableDates : SelectableDates {

    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis < System.currentTimeMillis()
    }

}

fun yearRange(inPast: Boolean): IntRange {
    return if (inPast) 1930..getCurrentYear()
    else getCurrentYear()..getCurrentYear() + 1
}

private fun getCurrentYear(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Year.now().value
    } else {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            .format(System.currentTimeMillis())
            .substring(0, 4)
            .toInt()
    }
}
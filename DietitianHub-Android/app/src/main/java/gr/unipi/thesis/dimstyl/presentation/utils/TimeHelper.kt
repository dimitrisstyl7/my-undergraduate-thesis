package gr.unipi.thesis.dimstyl.presentation.utils

fun getTimeIn12Hour(hour: Int, minute: Int): String {
    val minuteStr = if (minute < 10) "0$minute" else minute.toString()

    return if (hour <= 12) {
        val hourStr = if (hour < 10) "0$hour" else hour.toString()
        "$hourStr:$minuteStr AM"
    } else {
        val hourStr = if (hour - 12 < 10) "0${hour - 12}" else "${hour - 12}"
        "$hourStr:$minuteStr PM"
    }
}
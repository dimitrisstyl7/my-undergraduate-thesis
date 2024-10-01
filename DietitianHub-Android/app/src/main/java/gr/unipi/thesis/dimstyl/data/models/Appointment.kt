package gr.unipi.thesis.dimstyl.data.models

data class Appointment(
    val id: Int = -1,
    val appointmentDateTime: String,
    val formattedAppointmentDateTime: String = ""
)

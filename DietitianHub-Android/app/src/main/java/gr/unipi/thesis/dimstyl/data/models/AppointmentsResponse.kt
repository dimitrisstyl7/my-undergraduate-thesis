package gr.unipi.thesis.dimstyl.data.models

data class AppointmentsResponse(
    val scheduledAppointments: List<Appointment>,
    val pendingAppointments: List<Appointment>,
    val completedAppointments: List<Appointment>,
    val declinedAppointments: List<Appointment>,
    val cancelledAppointments: List<Appointment>
)

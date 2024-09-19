package gr.unipi.thesis.dimstyl.ui.screens.appointments

import gr.unipi.thesis.dimstyl.data.model.Appointment

data class AppointmentsState(
    val appointments: List<Appointment> = emptyList(),
    val requestedDate: String = "",
    val requestedTime: String = "",
    val showNewAppointmentDialog: Boolean = false,
    val showCancelAppointmentDialog: Boolean = false,
    val showDatePickerDialog: Boolean = false,
    val showTimePickerDialog: Boolean = false,
    val isLoading: Boolean = false
)

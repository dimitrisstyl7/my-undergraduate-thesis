package gr.unipi.thesis.dimstyl.ui.screens.appointments

import androidx.lifecycle.ViewModel
import gr.unipi.thesis.dimstyl.data.model.Appointment
import gr.unipi.thesis.dimstyl.ui.helpers.convertMillisToDate
import gr.unipi.thesis.dimstyl.ui.helpers.getTimeIn12Hour
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppointmentsViewModel : ViewModel() {

    private val _state = MutableStateFlow(AppointmentsState())
    val state = _state.asStateFlow()

    init {
        fetchAppointments()
    }

    private fun fetchAppointments() {
        _state.value = AppointmentsState(isLoading = true)

        // TODO: Fetch appointments from the server
        val appointments = listOf(
            Appointment(24, "3 Jan 2023, 01.00 PM"),
            Appointment(25, "3 Feb 2023, 02.00 PM"),
            Appointment(26, "3 Mar 2023, 10.00 AM"),
            Appointment(27, "3 Apr 2023, 05.00 PM"),
            Appointment(28, "3 May 2023, 01.00 PM"),
            Appointment(29, "3 Jun 2023, 03.00 PM"),
            Appointment(30, "3 Jul 2023, 01.00 PM"),
            Appointment(31, "3 Aug 2023, 02.30 PM"),
            Appointment(32, "3 Sep 2023, 03.30 PM"),
            Appointment(33, "3 Oct 2023, 11.00 AM"),
            Appointment(34, "3 Nov 2023, 01.00 PM")
        )

        _state.value = AppointmentsState(appointments = appointments, isLoading = false)
    }

    fun showNewAppointmentDialog(show: Boolean) {
        _state.value = _state.value.copy(showNewAppointmentDialog = show)
    }

    fun showCancelAppointmentDialog(show: Boolean) {
        _state.value = _state.value.copy(showCancelAppointmentDialog = show)
    }

    fun showDatePickerDialog(show: Boolean) {
        _state.value = _state.value.copy(showDatePickerDialog = show)
    }

    fun showTimePickerDialog(show: Boolean) {
        _state.value = _state.value.copy(showTimePickerDialog = show)
    }

    fun setRequestedDate(date: Long?) {
        if (date == null) return
        _state.value = _state.value.copy(requestedDate = convertMillisToDate(date))
    }

    fun setRequestedTime(hour: Int, minute: Int) {
        _state.value = _state.value.copy(requestedTime = getTimeIn12Hour(hour, minute))
    }

    fun clearDateTime() {
        _state.value = _state.value.copy(requestedDate = "", requestedTime = "")
    }

}

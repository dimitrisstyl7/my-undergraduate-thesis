package gr.unipi.thesis.dimstyl.ui.screens.appointments

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.lifecycle.ViewModel
import gr.unipi.thesis.dimstyl.data.model.Appointment
import gr.unipi.thesis.dimstyl.data.model.AppointmentStatus
import gr.unipi.thesis.dimstyl.ui.components.table.CellData
import gr.unipi.thesis.dimstyl.ui.components.table.HeaderCellData
import gr.unipi.thesis.dimstyl.ui.components.table.createActionableTableRowsData
import gr.unipi.thesis.dimstyl.ui.components.table.createSimpleTableRowsData
import gr.unipi.thesis.dimstyl.ui.helpers.convertMillisToDate
import gr.unipi.thesis.dimstyl.ui.helpers.getTimeIn12Hour
import gr.unipi.thesis.dimstyl.ui.theme.DangerColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppointmentsViewModel : ViewModel() {

    private val _state = MutableStateFlow(AppointmentsState())
    val state = _state.asStateFlow()

    lateinit var scheduledTableHeaderCellsData: List<HeaderCellData>
    lateinit var pendingTableHeaderCellsData: List<HeaderCellData>
    lateinit var completedTableHeaderCellsData: List<HeaderCellData>
    lateinit var declinedTableHeaderCellsData: List<HeaderCellData>
    lateinit var cancelledTableHeaderCellsData: List<HeaderCellData>


    init {
        initializeTableHeaderCellsData()
        fetchAppointments()
    }

    private fun initializeTableHeaderCellsData() {
        scheduledTableHeaderCellsData = listOf(
            HeaderCellData(weight = 0.2f, text = "#"),
            HeaderCellData(weight = 0.5f, text = "Scheduled At"),
            HeaderCellData(weight = 0.3f, text = "Actions")
        )
        pendingTableHeaderCellsData = listOf(
            HeaderCellData(weight = 0.2f, text = "#"),
            HeaderCellData(weight = 0.5f, text = "Requested At"),
            HeaderCellData(weight = 0.3f, text = "Actions")
        )
        completedTableHeaderCellsData = listOf(
            HeaderCellData(weight = 0.2f, text = "#"),
            HeaderCellData(weight = 0.5f, text = "Scheduled At"),
            HeaderCellData(weight = 0.3f, text = "Actions")
        )
        declinedTableHeaderCellsData = listOf(
            HeaderCellData(weight = 0.2f, text = "#"),
            HeaderCellData(weight = 0.5f, text = "Requested At"),
            HeaderCellData(weight = 0.3f, text = "Actions")
        )
        cancelledTableHeaderCellsData = listOf(
            HeaderCellData(weight = 0.2f, text = "#"),
            HeaderCellData(weight = 0.5f, text = "Requested At"),
            HeaderCellData(weight = 0.3f, text = "Actions")
        )
    }

    private fun fetchAppointments() {
        _state.value = _state.value.copy(isLoading = true)

        // TODO: Fetch appointments from the server
        val scheduledAppointments = listOf(
            Appointment(24, "3 Jan 2023, 01.00 PM", AppointmentStatus.SCHEDULED),
            Appointment(25, "3 Feb 2023, 02.00 PM", AppointmentStatus.SCHEDULED),
            Appointment(26, "13 Mar 2023, 10.00 AM", AppointmentStatus.SCHEDULED),
            Appointment(27, "3 Apr 2023, 05.00 PM", AppointmentStatus.SCHEDULED),
            Appointment(28, "13 May 2023, 01.00 PM", AppointmentStatus.SCHEDULED)
        )
        val pendingAppointments = listOf(
            Appointment(35, "3 Dec 2023, 01.00 PM", AppointmentStatus.PENDING),
            Appointment(36, "13 Jan 2024, 02.00 PM", AppointmentStatus.PENDING),
            Appointment(37, "3 Feb 2024, 10.00 AM", AppointmentStatus.PENDING),
            Appointment(38, "3 Mar 2024, 05.00 PM", AppointmentStatus.PENDING),
            Appointment(39, "13 Apr 2024, 01.00 PM", AppointmentStatus.PENDING)
        )
        val completedAppointments = listOf(
            Appointment(46, "3 Nov 2024, 01.00 PM", AppointmentStatus.COMPLETED),
            Appointment(47, "3 Dec 2024, 02.00 PM", AppointmentStatus.COMPLETED),
            Appointment(48, "13 Jan 2025, 10.00 AM", AppointmentStatus.COMPLETED),
            Appointment(49, "3 Feb 2025, 05.00 PM", AppointmentStatus.COMPLETED),
            Appointment(50, "3 Mar 2025, 01.00 PM", AppointmentStatus.COMPLETED)
        )
        val declinedAppointments = listOf(
            Appointment(57, "23 Oct 2025, 01.00 PM", AppointmentStatus.DECLINED),
            Appointment(58, "3 Nov 2025, 02.00 PM", AppointmentStatus.DECLINED),
            Appointment(59, "3 Dec 2025, 10.00 AM", AppointmentStatus.DECLINED),
            Appointment(60, "23 Jan 2026, 05.00 PM", AppointmentStatus.DECLINED),
            Appointment(61, "3 Feb 2026, 01.00 PM", AppointmentStatus.DECLINED)
        )
        val cancelledAppointments = listOf(
            Appointment(68, "3 Sep 2026, 01.00 PM", AppointmentStatus.CANCELLED),
            Appointment(69, "13 Oct 2026, 02.00 PM", AppointmentStatus.CANCELLED),
            Appointment(70, "3 Nov 2026, 10.00 AM", AppointmentStatus.CANCELLED),
            Appointment(71, "3 Dec 2026, 05.00 PM", AppointmentStatus.CANCELLED),
            Appointment(72, "13 Jan 2027, 01.00 PM", AppointmentStatus.CANCELLED)
        )

        _state.value = _state.value.copy(
            scheduledTableRowsData = createActionableAppointmentTableRowsData(scheduledAppointments),
            pendingTableRowsData = createActionableAppointmentTableRowsData(pendingAppointments),
            completedTableRowsData = createSimpleAppointmentTableRowsData(completedAppointments),
            declinedTableRowsData = createSimpleAppointmentTableRowsData(declinedAppointments),
            cancelledTableRowsData = createSimpleAppointmentTableRowsData(cancelledAppointments),
            isLoading = false
        )
    }

    private fun createActionableAppointmentTableRowsData(appointments: List<Appointment>): List<List<CellData>> {
        return createActionableTableRowsData(
            items = appointments,
            getText = { appointment -> appointment.start },
            icon = { appointment ->
                {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = "Cancel the appointment requested at ${appointment.start}"
                    )
                }
            },
            buttonColor = DangerColor,
            onClick = { showCancelAppointmentDialog(true) }
        )
    }

    private fun createSimpleAppointmentTableRowsData(appointments: List<Appointment>): List<List<CellData>> {
        return createSimpleTableRowsData(
            items = appointments,
            getText = { appointment -> appointment.start }
        )
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

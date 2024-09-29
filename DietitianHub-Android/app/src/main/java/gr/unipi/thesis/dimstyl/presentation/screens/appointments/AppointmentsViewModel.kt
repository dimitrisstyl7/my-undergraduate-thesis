package gr.unipi.thesis.dimstyl.presentation.screens.appointments

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.lifecycle.ViewModel
import gr.unipi.thesis.dimstyl.data.models.Appointment
import gr.unipi.thesis.dimstyl.domain.models.AppointmentStatus
import gr.unipi.thesis.dimstyl.presentation.components.table.CellData
import gr.unipi.thesis.dimstyl.presentation.components.table.HeaderCellData
import gr.unipi.thesis.dimstyl.presentation.components.table.createEmptyTableRowsData
import gr.unipi.thesis.dimstyl.presentation.components.table.createTableRowsData
import gr.unipi.thesis.dimstyl.presentation.theme.DangerColor
import gr.unipi.thesis.dimstyl.presentation.utils.convertMillisToDate
import gr.unipi.thesis.dimstyl.presentation.utils.getTimeIn12Hour
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppointmentsViewModel : ViewModel() {

    private val _state = MutableStateFlow(AppointmentsState())
    val state = _state.asStateFlow()

    private val cellsWeight = listOf(0.2f, 0.6f, 0.2f)
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
            HeaderCellData(weight = cellsWeight[0], text = "#"),
            HeaderCellData(weight = cellsWeight[1], text = "Scheduled At"),
            HeaderCellData(weight = cellsWeight[2], text = "Actions")
        )
        pendingTableHeaderCellsData = listOf(
            HeaderCellData(weight = cellsWeight[0], text = "#"),
            HeaderCellData(weight = cellsWeight[1], text = "Requested At"),
            HeaderCellData(weight = cellsWeight[2], text = "Actions")
        )
        completedTableHeaderCellsData = listOf(
            HeaderCellData(weight = cellsWeight[0], text = "#"),
            HeaderCellData(weight = cellsWeight[1], text = "Scheduled At"),
            HeaderCellData(weight = cellsWeight[2], text = "Actions")
        )
        declinedTableHeaderCellsData = listOf(
            HeaderCellData(weight = cellsWeight[0], text = "#"),
            HeaderCellData(weight = cellsWeight[1], text = "Requested At"),
            HeaderCellData(weight = cellsWeight[2], text = "Actions")
        )
        cancelledTableHeaderCellsData = listOf(
            HeaderCellData(weight = cellsWeight[0], text = "#"),
            HeaderCellData(weight = cellsWeight[1], text = "Requested At"),
            HeaderCellData(weight = cellsWeight[2], text = "Actions")
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

        val scheduledTableRowsData =
            if (scheduledAppointments.isEmpty()) createEmptyTableRowsData("No scheduled appointments found")
            else createAppointmentTableRowsData(
                appointments = scheduledAppointments,
                isActionable = true
            )
        val pendingTableRowsData =
            if (pendingAppointments.isEmpty()) createEmptyTableRowsData("No pending appointments found")
            else createAppointmentTableRowsData(
                appointments = pendingAppointments,
                isActionable = true
            )
        val completedTableRowsData =
            if (completedAppointments.isEmpty()) createEmptyTableRowsData("No completed appointments found")
            else createAppointmentTableRowsData(
                appointments = completedAppointments,
                isActionable = false
            )
        val declinedTableRowsData =
            if (declinedAppointments.isEmpty()) createEmptyTableRowsData("No declined appointments found")
            else createAppointmentTableRowsData(
                appointments = declinedAppointments,
                isActionable = false
            )
        val cancelledTableRowsData =
            if (cancelledAppointments.isEmpty()) createEmptyTableRowsData("No cancelled appointments found")
            else createAppointmentTableRowsData(
                appointments = cancelledAppointments,
                isActionable = false
            )

        _state.value = _state.value.copy(
            scheduledTableRowsData = scheduledTableRowsData,
            pendingTableRowsData = pendingTableRowsData,
            completedTableRowsData = completedTableRowsData,
            declinedTableRowsData = declinedTableRowsData,
            cancelledTableRowsData = cancelledTableRowsData,
            isLoading = false
        )
    }

    private fun createAppointmentTableRowsData(
        appointments: List<Appointment>,
        isActionable: Boolean
    ): List<List<CellData>> {
        return createTableRowsData(
            cellsWeight = cellsWeight,
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
            onClick = { showCancelAppointmentDialog(true) },
            isActionable = isActionable
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

package gr.unipi.thesis.dimstyl.presentation.screens.appointments

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.unipi.thesis.dimstyl.data.models.Appointment
import gr.unipi.thesis.dimstyl.domain.usecases.CancelAppointmentUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.CreateAppointmentUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.FetchAppointmentsUseCase
import gr.unipi.thesis.dimstyl.presentation.components.table.CellData
import gr.unipi.thesis.dimstyl.presentation.components.table.HeaderCellData
import gr.unipi.thesis.dimstyl.presentation.components.table.createEmptyTableRowsData
import gr.unipi.thesis.dimstyl.presentation.components.table.createTableRowsData
import gr.unipi.thesis.dimstyl.presentation.theme.DangerColor
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.CANCEL_APPOINTMENT_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.CREATE_APPOINTMENT_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.FETCH_APPOINTMENTS_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.SuccessMessages.APPOINTMENT_CANCELLED_SUCCESS_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.SuccessMessages.APPOINTMENT_CREATED_SUCCESS_MESSAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class AppointmentsViewModel(
    private val fetchAppointmentsUseCase: FetchAppointmentsUseCase,
    private val createAppointmentUseCase: CreateAppointmentUseCase,
    private val cancelAppointmentUseCase: CancelAppointmentUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AppointmentsState())
    val state = _state.asStateFlow()

    private val cellsWeight = listOf(0.2f, 0.6f, 0.2f)
    lateinit var scheduledTableHeaderCellsData: List<HeaderCellData>
    lateinit var pendingTableHeaderCellsData: List<HeaderCellData>
    lateinit var completedTableHeaderCellsData: List<HeaderCellData>
    lateinit var declinedTableHeaderCellsData: List<HeaderCellData>
    lateinit var cancelledTableHeaderCellsData: List<HeaderCellData>

    private val scheduledAppointments: MutableList<Appointment> = mutableListOf()
    private val pendingAppointments: MutableList<Appointment> = mutableListOf()
    private val cancelledAppointments: MutableList<Appointment> = mutableListOf()

    init {
        initializeTableHeaderCellsData()
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

    fun fetchAppointments(onFetchAppointmentsResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val result = fetchAppointmentsUseCase()
            val appointments = result.getOrNull()

            var scheduledTableRowsData: List<List<CellData>> =
                createEmptyTableRowsData("No scheduled appointments found")
            var pendingTableRowsData: List<List<CellData>> =
                createEmptyTableRowsData("No pending appointments found")
            var completedTableRowsData: List<List<CellData>> =
                createEmptyTableRowsData("No completed appointments found")
            var declinedTableRowsData: List<List<CellData>> =
                createEmptyTableRowsData("No declined appointments found")
            var cancelledTableRowsData: List<List<CellData>> =
                createEmptyTableRowsData("No cancelled appointments found")

            if (result.isSuccess && appointments != null) {
                scheduledAppointments.clear()
                pendingAppointments.clear()
                cancelledAppointments.clear()
                scheduledAppointments.addAll(appointments.scheduledAppointments)
                pendingAppointments.addAll(appointments.pendingAppointments)
                cancelledAppointments.addAll(appointments.cancelledAppointments)
                val completedAppointments = appointments.completedAppointments
                val declinedAppointments = appointments.declinedAppointments

                scheduledTableRowsData =
                    if (scheduledAppointments.isEmpty()) createEmptyTableRowsData("No scheduled appointments found")
                    else createTableRowsData(scheduledAppointments, isActionable = true)

                pendingTableRowsData =
                    if (pendingAppointments.isEmpty()) createEmptyTableRowsData("No pending appointments found")
                    else createTableRowsData(pendingAppointments, isActionable = true)

                completedTableRowsData =
                    if (completedAppointments.isEmpty()) createEmptyTableRowsData("No completed appointments found")
                    else createTableRowsData(completedAppointments, isActionable = false)

                declinedTableRowsData =
                    if (declinedAppointments.isEmpty()) createEmptyTableRowsData("No declined appointments found")
                    else createTableRowsData(declinedAppointments, isActionable = false)

                cancelledTableRowsData =
                    if (cancelledAppointments.isEmpty()) createEmptyTableRowsData("No cancelled appointments found")
                    else createTableRowsData(cancelledAppointments, isActionable = false)
            } else {
                val errorMessage =
                    result.exceptionOrNull()?.message ?: FETCH_APPOINTMENTS_ERROR_MESSAGE
                onFetchAppointmentsResult(errorMessage, false)
            }

            _state.value = _state.value.copy(
                scheduledTableRowsData = scheduledTableRowsData,
                pendingTableRowsData = pendingTableRowsData,
                completedTableRowsData = completedTableRowsData,
                declinedTableRowsData = declinedTableRowsData,
                cancelledTableRowsData = cancelledTableRowsData,
                isLoading = false
            )
        }
    }

    fun createAppointment(onCreateAppointmentResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            val requestedDatetime = _state.value.requestedDatetime
            val result = createAppointmentUseCase(requestedDatetime)
            val appointment = result.getOrNull()

            if (result.isSuccess && appointment != null) {
                pendingAppointments.addIndexed(appointment)
                val appointmentsTableRowsData =
                    createTableRowsData(pendingAppointments, isActionable = true)
                onCreateAppointmentResult(APPOINTMENT_CREATED_SUCCESS_MESSAGE, true)
                _state.value = _state.value.copy(pendingTableRowsData = appointmentsTableRowsData)
            } else {
                val errorMessage =
                    result.exceptionOrNull()?.message ?: CREATE_APPOINTMENT_ERROR_MESSAGE
                onCreateAppointmentResult(errorMessage, false)
            }

            clearDateTime()
            showNewAppointmentDialog(false)
        }
    }

    fun cancelAppointment(onCancelAppointmentResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            val id = _state.value.appointmentToBeCancelledId
            val result = cancelAppointmentUseCase(id)

            if (result.isSuccess) {
                // Find the cancelled appointment and move it to the cancelled list
                var index = scheduledAppointments.indexOfFirst { it.id == id }
                val fromScheduled = index != -1
                index =
                    if (fromScheduled) index else pendingAppointments.indexOfFirst { it.id == id }

                val appointments = if (fromScheduled) scheduledAppointments else pendingAppointments
                val appointment = appointments.removeAt(index)
                cancelledAppointments.addIndexed(appointment, ascending = false)

                val appointmentsTableRowsData =
                    createTableRowsData(appointments, isActionable = true)
                val cancelledTableRowsData =
                    createTableRowsData(cancelledAppointments, isActionable = false)

                _state.value =
                    if (fromScheduled) _state.value.copy(
                        scheduledTableRowsData = appointmentsTableRowsData,
                        cancelledTableRowsData = cancelledTableRowsData
                    )
                    else _state.value.copy(
                        pendingTableRowsData = appointmentsTableRowsData,
                        cancelledTableRowsData = cancelledTableRowsData
                    )

                onCancelAppointmentResult(APPOINTMENT_CANCELLED_SUCCESS_MESSAGE, true)
            } else {
                val errorMessage =
                    result.exceptionOrNull()?.message ?: CANCEL_APPOINTMENT_ERROR_MESSAGE
                onCancelAppointmentResult(errorMessage, false)
            }

            showCancelAppointmentDialog(false)
            _state.value = _state.value.copy(appointmentToBeCancelledId = -1)
        }
    }

    private fun createTableRowsData(
        appointments: List<Appointment>,
        isActionable: Boolean
    ): List<List<CellData>> {
        return createTableRowsData(
            cellsWeight = cellsWeight,
            items = appointments,
            getText = { appointment -> appointment.formattedAppointmentDateTime },
            icon = { appointment ->
                {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        contentDescription = "Cancel the appointment requested at ${appointment.formattedAppointmentDateTime}"
                    )
                }
            },
            buttonColor = DangerColor,
            isActionable = isActionable,
            onClick = { id ->
                setAppointmentToBeCancelledId(id)
                showCancelAppointmentDialog(true)
            }
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

    fun setRequestedDate(selectedDateMillis: Long) {
        val selectedDate =
            Instant.ofEpochMilli(selectedDateMillis).atZone(ZoneId.systemDefault()).toLocalDate()
        _state.value = _state.value.copy(requestedDate = selectedDate.toString())
    }

    fun setRequestedTime(time: LocalTime) {
        _state.value = _state.value.copy(requestedTime = time.toString())
    }

    fun clearDateTime() {
        _state.value = _state.value.copy(requestedDate = "", requestedTime = "")
    }

    private fun setAppointmentToBeCancelledId(id: Int) {
        _state.value = _state.value.copy(appointmentToBeCancelledId = id)
    }

    private fun MutableList<Appointment>.addIndexed(
        newAppointment: Appointment,
        ascending: Boolean = true
    ) {
        if (isEmpty()) {
            add(newAppointment)
            return
        }

        for (index in this.indices) {
            val appointment = this[index]
            val newAppointmentDateTime = LocalDateTime.parse(newAppointment.appointmentDateTime)
            val appointmentDateTime = LocalDateTime.parse(appointment.appointmentDateTime)

            if (ascending) {
                if (newAppointmentDateTime.isBefore(appointmentDateTime)) {
                    add(index, newAppointment)
                    return
                }
            } else {
                if (newAppointmentDateTime.isAfter(appointmentDateTime)) {
                    add(index, newAppointment)
                    return
                }
            }

            if (index == size - 1) {
                add(newAppointment)
                return
            }
        }
    }

}

package gr.unipi.thesis.dimstyl.presentation.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.unipi.thesis.dimstyl.data.models.Appointment
import gr.unipi.thesis.dimstyl.domain.usecases.CancelAppointmentUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.FetchHomeDataUseCase
import gr.unipi.thesis.dimstyl.presentation.components.table.CellData
import gr.unipi.thesis.dimstyl.presentation.components.table.HeaderCellData
import gr.unipi.thesis.dimstyl.presentation.components.table.createEmptyTableRowData
import gr.unipi.thesis.dimstyl.presentation.components.table.createTableRowsData
import gr.unipi.thesis.dimstyl.presentation.theme.DangerColor
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.CANCEL_APPOINTMENT_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.FETCH_DATA_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.SuccessMessages.APPOINTMENT_CANCELLED_SUCCESS_MESSAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val fetchHomeDataUseCase: FetchHomeDataUseCase,
    private val cancelAppointmentUseCase: CancelAppointmentUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val cellsWeight = listOf(0.2f, 0.6f, 0.2f)
    lateinit var appointmentsTableHeaderCellsData: List<HeaderCellData>

    private val appointments: MutableList<Appointment> = mutableListOf()

    init {
        initializeTableHeaderCellsData()
    }

    private fun initializeTableHeaderCellsData() {
        appointmentsTableHeaderCellsData = listOf(
            HeaderCellData(weight = cellsWeight[0], text = "#"),
            HeaderCellData(weight = cellsWeight[1], text = "Scheduled At"),
            HeaderCellData(weight = cellsWeight[2], text = "Actions")
        )
    }

    fun fetchHomeData(onFetchHomeDataResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val result = fetchHomeDataUseCase()
            val homeData = result.getOrNull()

            if (result.isSuccess && homeData != null) {
                appointments.clear()
                val fullName = homeData.fullName
                val articles = homeData.articles
                val announcements = homeData.announcements
                appointments.addAll(homeData.appointments)
                val appointmentsTableRowsData = createTableRowsData(appointments)

                _state.value =
                    _state.value.copy(
                        fullName = fullName,
                        articles = articles,
                        announcements = announcements,
                        appointmentsTableRowsData = appointmentsTableRowsData,
                        isLoading = false
                    )
            } else {
                val appointmentsTableRowsData = createTableRowsData(appointments)
                val errorMessage = result.exceptionOrNull()?.message ?: FETCH_DATA_ERROR_MESSAGE

                onFetchHomeDataResult(errorMessage, false)

                _state.value = _state.value.copy(
                    appointmentsTableRowsData = appointmentsTableRowsData,
                    isLoading = false
                )
            }
        }
    }

    fun cancelAppointment(onCancelAppointmentResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            val id = _state.value.appointmentToBeCancelledId
            val result = cancelAppointmentUseCase(id)

            if (result.isSuccess) {
                appointments.removeIf { it.id == id } // Remove the cancelled appointment from the list
                val appointmentsTableRowsData = createTableRowsData(appointments)
                _state.value =
                    _state.value.copy(appointmentsTableRowsData = appointmentsTableRowsData)
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

    fun showCancelAppointmentDialog(show: Boolean) {
        _state.value = _state.value.copy(showCancelAppointmentDialog = show)
    }

    private fun setAppointmentToBeCancelledId(id: Int) {
        _state.value = _state.value.copy(appointmentToBeCancelledId = id)
    }

    private fun createTableRowsData(appointments: List<Appointment>): List<List<CellData>> {
        return if (appointments.isEmpty()) createEmptyTableRowData("Appointments not found")
        else createTableRowsData(
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
            onClick = { id ->
                setAppointmentToBeCancelledId(id)
                showCancelAppointmentDialog(true)
            }
        )
    }

}
package gr.unipi.thesis.dimstyl.presentation.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.unipi.thesis.dimstyl.data.models.Announcement
import gr.unipi.thesis.dimstyl.data.models.Appointment
import gr.unipi.thesis.dimstyl.data.models.Article
import gr.unipi.thesis.dimstyl.domain.usecases.CancelAppointmentUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.FetchHomeDataUseCase
import gr.unipi.thesis.dimstyl.presentation.components.table.CellData
import gr.unipi.thesis.dimstyl.presentation.components.table.HeaderCellData
import gr.unipi.thesis.dimstyl.presentation.components.table.createEmptyTableRowsData
import gr.unipi.thesis.dimstyl.presentation.components.table.createTableRowsData
import gr.unipi.thesis.dimstyl.presentation.theme.DangerColor
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

    fun fetchHomeData(onSnackbarShow: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val result = fetchHomeDataUseCase.execute()
            val homeData = result.getOrNull()
            var isLoading = _state.value.isLoading
            var fullName: String? = _state.value.fullName
            var articles: List<Article> = _state.value.articles
            var announcements: List<Announcement> = _state.value.announcements
            var appointmentsTableRowsData: List<List<CellData>> =
                _state.value.appointmentsTableRowsData

            if (result.isSuccess && homeData != null) {
                isLoading = false
                fullName = homeData.fullName
                articles = homeData.articles
                announcements = homeData.announcements
                appointments.addAll(homeData.appointments)
                appointmentsTableRowsData = createTableRowsData(
                    appointments = appointments,
                    onCancelAppointment = { id ->
                        setAppointmentToBeCanceledId(id)
                        showCancelAppointmentDialog(true)
                    }
                )
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: FETCH_DATA_ERROR_MESSAGE
                onSnackbarShow(errorMessage, false)
            }

            _state.value =
                _state.value.copy(
                    fullName = fullName,
                    articles = articles,
                    announcements = announcements,
                    appointmentsTableRowsData = appointmentsTableRowsData,
                    isLoading = isLoading
                )
        }
    }

    fun cancelAppointment(onCancelAppointmentResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            val id = _state.value.appointmentToBeCanceledId
            val result = cancelAppointmentUseCase.execute(id)

            if (result.isSuccess) {
                removeCancelledAppointmentFromTable(id)
                @Suppress("NAME_SHADOWING") val appointmentsTableRowsData =
                    createTableRowsData(
                        appointments = appointments,
                        onCancelAppointment = { id -> setAppointmentToBeCanceledId(id) }
                    )
                _state.value =
                    _state.value.copy(appointmentsTableRowsData = appointmentsTableRowsData)
                onCancelAppointmentResult(APPOINTMENT_CANCELLED_SUCCESS_MESSAGE, true)
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: FETCH_DATA_ERROR_MESSAGE
                onCancelAppointmentResult(errorMessage, false)
            }

            showCancelAppointmentDialog(false)
        }
    }

    fun showCancelAppointmentDialog(show: Boolean) {
        _state.value = _state.value.copy(showCancelAppointmentDialog = show)
    }

    private fun setAppointmentToBeCanceledId(id: Int) {
        _state.value = _state.value.copy(appointmentToBeCanceledId = id)
    }

    private fun removeCancelledAppointmentFromTable(id: Int) {
        appointments.removeIf { it.id == id }
    }

    private fun createTableRowsData(
        appointments: List<Appointment>,
        onCancelAppointment: (Int) -> Unit
    ): List<List<CellData>> {
        return if (appointments.isEmpty()) createEmptyTableRowsData("No appointments found")
        else createTableRowsData(
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
            onClick = { id -> onCancelAppointment(id) }
        )
    }

}
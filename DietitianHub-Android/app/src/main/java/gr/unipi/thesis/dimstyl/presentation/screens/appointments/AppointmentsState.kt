package gr.unipi.thesis.dimstyl.presentation.screens.appointments

import gr.unipi.thesis.dimstyl.presentation.components.table.CellData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class AppointmentsState(
    val scheduledTableRowsData: List<List<CellData>> = emptyList(),
    val pendingTableRowsData: List<List<CellData>> = emptyList(),
    val completedTableRowsData: List<List<CellData>> = emptyList(),
    val declinedTableRowsData: List<List<CellData>> = emptyList(),
    val cancelledTableRowsData: List<List<CellData>> = emptyList(),
    val requestedDate: String = "",
    val requestedTime: String = "",
    val appointmentToBeCancelledId: Int = -1,
    val showNewAppointmentDialog: Boolean = false,
    val showCancelAppointmentDialog: Boolean = false,
    val showDatePickerDialog: Boolean = false,
    val showTimePickerDialog: Boolean = false,
    val isLoading: Boolean = true
) {
    val requestedDatetime: LocalDateTime
        get() = LocalDateTime.of(LocalDate.parse(requestedDate), LocalTime.parse(requestedTime))

}

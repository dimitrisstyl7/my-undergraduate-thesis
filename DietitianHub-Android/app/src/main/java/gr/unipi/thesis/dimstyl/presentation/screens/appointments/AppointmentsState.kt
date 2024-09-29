package gr.unipi.thesis.dimstyl.presentation.screens.appointments

import gr.unipi.thesis.dimstyl.presentation.components.table.CellData

data class AppointmentsState(
    val scheduledTableRowsData: List<List<CellData>> = emptyList(),
    val pendingTableRowsData: List<List<CellData>> = emptyList(),
    val completedTableRowsData: List<List<CellData>> = emptyList(),
    val declinedTableRowsData: List<List<CellData>> = emptyList(),
    val cancelledTableRowsData: List<List<CellData>> = emptyList(),
    val requestedDate: String = "",
    val requestedTime: String = "",
    val showNewAppointmentDialog: Boolean = false,
    val showCancelAppointmentDialog: Boolean = false,
    val showDatePickerDialog: Boolean = false,
    val showTimePickerDialog: Boolean = false,
    val isLoading: Boolean = false
)

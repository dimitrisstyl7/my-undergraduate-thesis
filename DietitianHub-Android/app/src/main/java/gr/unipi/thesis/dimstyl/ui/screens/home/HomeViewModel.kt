package gr.unipi.thesis.dimstyl.ui.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.lifecycle.ViewModel
import gr.unipi.thesis.dimstyl.data.model.Announcement
import gr.unipi.thesis.dimstyl.data.model.Appointment
import gr.unipi.thesis.dimstyl.data.model.Article
import gr.unipi.thesis.dimstyl.domain.model.AppointmentStatus
import gr.unipi.thesis.dimstyl.ui.components.table.HeaderCellData
import gr.unipi.thesis.dimstyl.ui.components.table.createEmptyTableRowsData
import gr.unipi.thesis.dimstyl.ui.components.table.createTableRowsData
import gr.unipi.thesis.dimstyl.ui.theme.DangerColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val cellsWeight = listOf(0.2f, 0.6f, 0.2f)
    lateinit var appointmentsTableHeaderCellsData: List<HeaderCellData>

    init {
        initializeTableHeaderCellsData()
        fetchHomeData()
    }

    private fun initializeTableHeaderCellsData() {
        appointmentsTableHeaderCellsData = listOf(
            HeaderCellData(weight = cellsWeight[0], text = "#"),
            HeaderCellData(weight = cellsWeight[1], text = "Scheduled At"),
            HeaderCellData(weight = cellsWeight[2], text = "Actions")
        )
    }

    private fun fetchHomeData() {
        _state.value = _state.value.copy(isLoading = true)

        // TODO: Fetch data from the server
        val articles = listOf(
            Article(
                1,
                "New recipe for a delicious cake",
                "16 Sep 2024\n07:54 PM"
            ),
            Article(
                2,
                "How to make a perfect coffee",
                "16 Sep 2024\n07:54 PM"
            ),
            Article(
                3,
                "The best places to visit in Greece",
                "16 Sep 2024\n07:54 PM"
            ),
            Article(
                4,
                "The benefits of a healthy lifestyle",
                "16 Sep 2024\n07:54 PM"
            )
        )
        val announcements = listOf(
            Announcement(
                1,
                "Announcement 1",
                "15 Sep 2024, 07:07 PM"
            ),
            Announcement(
                2,
                "New Holidays ahead! get ready! Can't wait to see you all there!",
                "15 Sep 2024, 07:07 PM"
            ),
            Announcement(
                3,
                "Announcement 3",
                "15 Sep 2024, 07:07 PM"
            ),
            Announcement(
                4,
                "Announcement 4",
                "15 Sep 2024, 07:07 PM"
            )
        )
        val appointments = listOf(
            Appointment(24, "3 Jan 2023, 01.00 PM", AppointmentStatus.SCHEDULED),
            Appointment(25, "3 Feb 2023, 02.00 PM", AppointmentStatus.SCHEDULED),
            Appointment(26, "13 Mar 2023, 10.00 AM", AppointmentStatus.SCHEDULED),
            Appointment(27, "3 Apr 2023, 05.00 PM", AppointmentStatus.SCHEDULED),
            Appointment(28, "13 May 2023, 01.00 PM", AppointmentStatus.SCHEDULED)
        )
        val appointmentsTableRowsData =
            if (appointments.isEmpty()) createEmptyTableRowsData("No appointments found")
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
                onClick = { /* TODO: showCancelAppointmentDialog(true) */ }
            )

        _state.value =
            _state.value.copy(
                fullName = "John Doe",
                articles = articles,
                announcements = announcements,
                appointmentsTableRowsData = appointmentsTableRowsData,
                isLoading = false
            )
    }

}
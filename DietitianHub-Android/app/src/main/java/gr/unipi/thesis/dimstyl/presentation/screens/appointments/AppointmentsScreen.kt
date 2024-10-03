package gr.unipi.thesis.dimstyl.presentation.screens.appointments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import gr.unipi.thesis.dimstyl.App
import gr.unipi.thesis.dimstyl.R
import gr.unipi.thesis.dimstyl.presentation.components.OutlinedTextField
import gr.unipi.thesis.dimstyl.presentation.components.SectionTitle
import gr.unipi.thesis.dimstyl.presentation.components.circularProgressIndicators.ScreenCircularProgressIndicator
import gr.unipi.thesis.dimstyl.presentation.components.dialogs.AlertDialog
import gr.unipi.thesis.dimstyl.presentation.components.dialogs.CancelAppointmentAlertDialog
import gr.unipi.thesis.dimstyl.presentation.components.dialogs.DatePickerDialog
import gr.unipi.thesis.dimstyl.presentation.components.dialogs.TimePickerDialog
import gr.unipi.thesis.dimstyl.presentation.components.table.Table
import gr.unipi.thesis.dimstyl.presentation.screens.appointments.components.FloatingActionButton
import gr.unipi.thesis.dimstyl.presentation.theme.DialogTextFieldInputColor
import gr.unipi.thesis.dimstyl.presentation.theme.NeutralColor
import gr.unipi.thesis.dimstyl.presentation.theme.PrimaryColor
import gr.unipi.thesis.dimstyl.presentation.theme.TopBarColor
import gr.unipi.thesis.dimstyl.presentation.utils.ContentType
import gr.unipi.thesis.dimstyl.presentation.utils.FutureSelectableDates
import gr.unipi.thesis.dimstyl.presentation.utils.viewModelFactory
import gr.unipi.thesis.dimstyl.presentation.utils.yearRange
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(
    viewModel: AppointmentsViewModel = viewModel<AppointmentsViewModel>(
        factory = viewModelFactory {
            AppointmentsViewModel(
                App.appModule.fetchAppointmentsUseCase,
                App.appModule.createAppointmentUseCase,
                App.appModule.cancelAppointmentUseCase
            )
        }
    ),
    onSnackbarShow: (String, Boolean) -> Unit
) {
    val initDate =
        LocalDate.now().plusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val appointmentsState by viewModel.state.collectAsStateWithLifecycle()
    val timePickerState =
        rememberTimePickerState(initialHour = 12, initialMinute = 0)
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initDate,
        yearRange = yearRange(inPast = false),
        selectableDates = FutureSelectableDates
    )

    if (appointmentsState.isLoading) {
        ScreenCircularProgressIndicator()
        LaunchedEffect(Unit) {
            viewModel.fetchAppointments(
                onFetchAppointmentsResult = { message, shortDuration ->
                    onSnackbarShow(message, shortDuration)
                }
            )
        }
    } else {
        Box(Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item(contentType = ContentType.SCHEDULED_APPOINTMENTS_TABLE) {
                    SectionTitle(
                        title = "Scheduled Appointments",
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .padding(vertical = 16.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Table(
                        modifier = Modifier.heightIn(max = 300.dp),
                        headerCells = viewModel.scheduledTableHeaderCellsData,
                        tableRows = appointmentsState.scheduledTableRowsData
                    )
                }

                item(contentType = ContentType.PENDING_APPOINTMENTS_TABLE) {
                    SectionTitle(
                        title = "Pending Appointments",
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .padding(vertical = 16.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Table(
                        modifier = Modifier.heightIn(max = 300.dp),
                        headerCells = viewModel.pendingTableHeaderCellsData,
                        tableRows = appointmentsState.pendingTableRowsData
                    )
                }

                item(contentType = ContentType.COMPLETED_APPOINTMENTS_TABLE) {
                    SectionTitle(
                        title = "Completed Appointments",
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .padding(vertical = 16.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Table(
                        modifier = Modifier.heightIn(max = 300.dp),
                        headerCells = viewModel.completedTableHeaderCellsData,
                        tableRows = appointmentsState.completedTableRowsData
                    )
                }

                item(contentType = ContentType.DECLINED_APPOINTMENTS_TABLE) {
                    SectionTitle(
                        title = "Declined Appointments",
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .padding(vertical = 16.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Table(
                        modifier = Modifier.heightIn(max = 300.dp),
                        headerCells = viewModel.declinedTableHeaderCellsData,
                        tableRows = appointmentsState.declinedTableRowsData
                    )
                }

                item(contentType = ContentType.CANCELLED_APPOINTMENTS_TABLE) {
                    SectionTitle(
                        title = "Cancelled Appointments",
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .padding(vertical = 16.dp),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Table(
                        modifier = Modifier.heightIn(max = 300.dp),
                        headerCells = viewModel.cancelledTableHeaderCellsData,
                        tableRows = appointmentsState.cancelledTableRowsData
                    )
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 20.dp, end = 20.dp),
                onClick = { viewModel.showNewAppointmentDialog(true) }
            )
        }
    }

    if (appointmentsState.showNewAppointmentDialog) {
        AlertDialog(
            title = "Request an Appointment",
            text = {
                Column {
                    OutlinedTextField(
                        label = "Date",
                        placeholder = { Text("Select a date") },
                        value = appointmentsState.requestedDate,
                        trailingIcon = {
                            IconButton(onClick = {
                                viewModel.showDatePickerDialog(true)
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.rounded_calendar_month_24),
                                    contentDescription = "Select a date"
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = DialogTextFieldInputColor,
                            unfocusedTextColor = DialogTextFieldInputColor,
                            focusedBorderColor = TopBarColor,
                            unfocusedBorderColor = Color.White,
                            focusedTrailingIconColor = TopBarColor,
                            unfocusedTrailingIconColor = Color.White,
                            focusedLabelColor = TopBarColor,
                            unfocusedLabelColor = Color.White,
                            focusedPlaceholderColor = TopBarColor
                        )
                    )

                    OutlinedTextField(
                        label = "Time",
                        placeholder = { Text("Select a time") },
                        value = appointmentsState.requestedTime,
                        trailingIcon = {
                            IconButton(onClick = {
                                viewModel.showTimePickerDialog(true)
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.rounded_nest_clock_farsight_analog_24),
                                    contentDescription = "Select a time"
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = DialogTextFieldInputColor,
                            unfocusedTextColor = DialogTextFieldInputColor,
                            focusedBorderColor = TopBarColor,
                            unfocusedBorderColor = Color.White,
                            focusedTrailingIconColor = TopBarColor,
                            unfocusedTrailingIconColor = Color.White,
                            focusedLabelColor = TopBarColor,
                            unfocusedLabelColor = Color.White,
                            focusedPlaceholderColor = TopBarColor
                        )
                    )
                }
            },
            confirmButtonText = "Send Request",
            dismissButtonText = "Close",
            icon = ImageVector.vectorResource(R.drawable.rounded_calendar_add_on_24),
            iconContentColor = PrimaryColor,
            confirmButtonContentColor = PrimaryColor,
            dismissButtonContentColor = NeutralColor,
            onConfirm = {
                if (appointmentsState.requestedDate.isEmpty() || appointmentsState.requestedTime.isEmpty()) {
                    onSnackbarShow("Please select a date and a time", true)
                    return@AlertDialog
                }
                viewModel.createAppointment(onCreateAppointmentResult = { message, duration ->
                    onSnackbarShow(message, duration)
                })
            },
            onDismiss = {
                viewModel.clearDateTime()
                viewModel.showNewAppointmentDialog(false)
            }
        )
    }

    if (appointmentsState.showDatePickerDialog) {
        DatePickerDialog(
            onDismiss = { viewModel.showDatePickerDialog(false) },
            onConfirm = {
                viewModel.showDatePickerDialog(false)
                val selectedDateMillis = datePickerState.selectedDateMillis ?: 0L
                viewModel.setRequestedDate(selectedDateMillis)
            },
            content = { DatePicker(state = datePickerState) }
        )
    }

    if (appointmentsState.showTimePickerDialog) {
        TimePickerDialog(
            onDismiss = { viewModel.showTimePickerDialog(false) },
            onConfirm = {
                viewModel.showTimePickerDialog(false)
                val selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                viewModel.setRequestedTime(selectedTime)
            },
            content = { TimePicker(state = timePickerState) }
        )
    }

    if (appointmentsState.showCancelAppointmentDialog) {
        CancelAppointmentAlertDialog(
            onConfirm = {
                viewModel.cancelAppointment(
                    onCancelAppointmentResult = { message, shortDuration ->
                        onSnackbarShow(message, shortDuration)
                    })
            },
            onDismiss = { viewModel.showCancelAppointmentDialog(false) }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AppointmentsScreenPreview() {
    AppointmentsScreen { _, _ -> }
}
package gr.unipi.thesis.dimstyl.ui.screens.appointments

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
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
import gr.unipi.thesis.dimstyl.R
import gr.unipi.thesis.dimstyl.ui.components.CircularProgressIndicator
import gr.unipi.thesis.dimstyl.ui.components.OutlinedTextField
import gr.unipi.thesis.dimstyl.ui.components.dialogs.AlertDialog
import gr.unipi.thesis.dimstyl.ui.components.dialogs.DatePickerDialog
import gr.unipi.thesis.dimstyl.ui.components.dialogs.TimePickerDialog
import gr.unipi.thesis.dimstyl.ui.components.table.Table
import gr.unipi.thesis.dimstyl.ui.helpers.ContentType
import gr.unipi.thesis.dimstyl.ui.helpers.FutureSelectableDates
import gr.unipi.thesis.dimstyl.ui.helpers.yearRange
import gr.unipi.thesis.dimstyl.ui.screens.appointments.components.FloatingActionButton
import gr.unipi.thesis.dimstyl.ui.theme.DialogTextFieldInputColor
import gr.unipi.thesis.dimstyl.ui.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.ui.theme.NeutralColor
import gr.unipi.thesis.dimstyl.ui.theme.PrimaryColor
import gr.unipi.thesis.dimstyl.ui.theme.TopBarColor
import gr.unipi.thesis.dimstyl.ui.theme.WarningColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AppointmentsScreen(
    viewModel: AppointmentsViewModel = viewModel(),
    backHandler: @Composable () -> Unit
) {
    val appointmentsState by viewModel.state.collectAsStateWithLifecycle()
    val timePickerState =
        rememberTimePickerState(initialHour = 12, initialMinute = 0, is24Hour = false)
    val datePickerState = rememberDatePickerState(
        yearRange = yearRange(inPast = false),
        selectableDates = FutureSelectableDates
    )

    backHandler()

    if (appointmentsState.isLoading) {
        CircularProgressIndicator()
    } else {
        Box(Modifier.fillMaxSize().background(TopBarColor)) {
            LazyColumn(Modifier.fillMaxSize()) {
                stickyHeader {
                    Text(
                        text = "Scheduled Appointments",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LeftBarColor)
                            .padding(vertical = 4.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(4.dp))
                }
                item(contentType = ContentType.SCHEDULED_APPOINTMENTS_TABLE) {
                    Table(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp),
                        headerCells = viewModel.scheduledTableHeaderCellsData,
                        tableRows = appointmentsState.scheduledTableRowsData
                    )
                }

                stickyHeader {
                    Text(
                        text = "Pending Appointments",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LeftBarColor)
                            .padding(vertical = 4.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(4.dp).background(TopBarColor))
                }
                item(contentType = ContentType.PENDING_APPOINTMENTS_TABLE) {
                    Table(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp),
                        headerCells = viewModel.pendingTableHeaderCellsData,
                        tableRows = appointmentsState.pendingTableRowsData
                    )
                }

                stickyHeader {
                    Text(
                        text = "Completed Appointments",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LeftBarColor)
                            .padding(vertical = 4.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(4.dp).background(TopBarColor))
                }
                item(contentType = ContentType.COMPLETED_APPOINTMENTS_TABLE) {
                    Table(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp),
                        headerCells = viewModel.completedTableHeaderCellsData,
                        tableRows = appointmentsState.completedTableRowsData
                    )
                }

                stickyHeader {
                    Text(
                        text = "Declined Appointments",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LeftBarColor)
                            .padding(vertical = 4.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(4.dp).background(TopBarColor))
                }
                item(contentType = ContentType.DECLINED_APPOINTMENTS_TABLE) {
                    Table(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp),
                        headerCells = viewModel.declinedTableHeaderCellsData,
                        tableRows = appointmentsState.declinedTableRowsData
                    )
                }

                stickyHeader {
                    Text(
                        text = "Cancelled Appointments",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LeftBarColor)
                            .padding(vertical = 4.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(4.dp).background(TopBarColor))
                }
                item(contentType = ContentType.CANCELLED_APPOINTMENTS_TABLE) {
                    Table(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp),
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
                        // TODO: Send the appointment request
                        viewModel.clearDateTime()
                        viewModel.showNewAppointmentDialog(false)
                    },
                    onDismiss = {
                        viewModel.clearDateTime()
                        viewModel.showNewAppointmentDialog(false)
                    }
                )
            }

            if (appointmentsState.showDatePickerDialog) {
                val onDismiss = { viewModel.showDatePickerDialog(false) }
                DatePickerDialog(
                    onDismiss = onDismiss,
                    onConfirm = {
                        viewModel.showDatePickerDialog(false)
                        viewModel.setRequestedDate(datePickerState.selectedDateMillis)
                    },
                    content = { DatePicker(state = datePickerState) }
                )
            }

            if (appointmentsState.showTimePickerDialog) {
                TimePickerDialog(
                    onDismiss = { viewModel.showTimePickerDialog(false) },
                    onConfirm = {
                        viewModel.showTimePickerDialog(false)
                        viewModel.setRequestedTime(timePickerState.hour, timePickerState.minute)
                    },
                    content = { TimePicker(state = timePickerState) }
                )
            }

            if (appointmentsState.showCancelAppointmentDialog) {
                AlertDialog(
                    title = "Cancel Appointment",
                    text = {
                        Text(
                            text = "You are about to cancel your appointment. This action cannot be undone. Are you sure?",
                            textAlign = TextAlign.Center
                        )
                    },
                    confirmButtonText = "Yes, I'm Sure",
                    dismissButtonText = "No, Go Back",
                    icon = Icons.Rounded.Warning,
                    iconContentColor = WarningColor,
                    onConfirm = {
                        // TODO: Cancel the appointment
                        viewModel.showCancelAppointmentDialog(false)
                    },
                    onDismiss = { viewModel.showCancelAppointmentDialog(false) }
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AppointmentsScreenPreview() {
    AppointmentsScreen {}
}
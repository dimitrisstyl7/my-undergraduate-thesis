package gr.unipi.thesis.dimstyl.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import gr.unipi.thesis.dimstyl.R
import gr.unipi.thesis.dimstyl.ui.helpers.PastSelectableDates
import gr.unipi.thesis.dimstyl.ui.helpers.yearRange
import gr.unipi.thesis.dimstyl.ui.screens.profile.components.ProfileOutlinedTextField
import gr.unipi.thesis.dimstyl.ui.theme.BodyColor
import gr.unipi.thesis.dimstyl.ui.theme.CancelButtonColor
import gr.unipi.thesis.dimstyl.ui.theme.EditButtonColor
import gr.unipi.thesis.dimstyl.ui.theme.InputTextColor
import gr.unipi.thesis.dimstyl.ui.theme.SaveButtonColor
import gr.unipi.thesis.dimstyl.ui.theme.TopBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = viewModel()) {
    val focusManager = LocalFocusManager.current
    val profileState by viewModel.state.collectAsState()
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = profileState.dateOfBirthMillis,
        yearRange = yearRange(inPast = true),
        selectableDates = PastSelectableDates
    )

    Column(Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                ProfileOutlinedTextField(
                    paddingValues = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp),
                    label = "First Name",
                    placeholder = "Enter first name",
                    value = profileState.firstName,
                    isError = false, // TODO: Add field validation
                    supportingText = null, // TODO: field validation message
                    onValueChange = { viewModel.setFirstName(it) },
                    readOnly = !profileState.inEditMode,
                    trailingIconVisible = profileState.firstName.isNotEmpty() && profileState.inEditMode,
                    onTrailingIconClick = { viewModel.setFirstName("") },
                    keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) }
                )
            }
            item {
                ProfileOutlinedTextField(
                    paddingValues = PaddingValues(horizontal = 16.dp),
                    label = "Last Name",
                    placeholder = "Enter last name",
                    value = profileState.lastName,
                    isError = false, // TODO: Add field validation
                    supportingText = null, // TODO: field validation message
                    onValueChange = { viewModel.setLastName(it) },
                    readOnly = !profileState.inEditMode,
                    trailingIconVisible = profileState.lastName.isNotEmpty() && profileState.inEditMode,
                    onTrailingIconClick = { viewModel.setLastName("") },
                    keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) }
                )
            }
            item {
                ProfileOutlinedTextField(
                    paddingValues = PaddingValues(horizontal = 16.dp),
                    label = "Email",
                    placeholder = "Enter email",
                    value = profileState.email,
                    isError = false, // TODO: Add field validation
                    supportingText = null, // TODO: field validation message
                    onValueChange = { viewModel.setEmail(it) },
                    readOnly = !profileState.inEditMode,
                    trailingIconVisible = profileState.email.isNotEmpty() && profileState.inEditMode,
                    onTrailingIconClick = { viewModel.setEmail("") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.None
                    ),
                    keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) }
                )
            }
            item {
                ProfileOutlinedTextField(
                    paddingValues = PaddingValues(horizontal = 16.dp),
                    label = "Phone",
                    placeholder = "Enter phone",
                    value = profileState.phone,
                    isError = false, // TODO: Add field validation
                    supportingText = null, // TODO: field validation message
                    onValueChange = { viewModel.setPhone(it) },
                    readOnly = !profileState.inEditMode,
                    trailingIconVisible = profileState.phone.isNotEmpty() && profileState.inEditMode,
                    onTrailingIconClick = { viewModel.setPhone("") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.None
                    ),
                    keyboardActions = KeyboardActions {
                        focusManager.moveFocus(FocusDirection.Next)
                        viewModel.setDropDownExpanded(true)
                    }
                )
            }
            item {
                Row(Modifier.width(480.dp)) {
                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .width(130.dp),
                        expanded = profileState.dropdownExpanded && profileState.inEditMode,
                        onExpandedChange = { viewModel.setDropDownExpanded(it) }
                    ) {
                        ProfileOutlinedTextField(
                            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                            label = "Gender",
                            value = profileState.gender.toString(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = profileState.dropdownExpanded && profileState.inEditMode
                                )
                            }
                        )

                        ExposedDropdownMenu(
                            containerColor = BodyColor,
                            expanded = profileState.dropdownExpanded && profileState.inEditMode,
                            onDismissRequest = { viewModel.setDropDownExpanded(false) }
                        ) {
                            Gender.entries.toList().forEach {
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = it.toString(),
                                            color = if (it == profileState.gender) TopBarColor else InputTextColor
                                        )
                                    },
                                    onClick = {
                                        viewModel.setGender(it)
                                        viewModel.setDropDownExpanded(false)
                                        focusManager.moveFocus(FocusDirection.Next)
                                    }
                                )
                            }
                        }
                    }

                    Box(Modifier.fillMaxWidth()) {
                        ProfileOutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, end = 16.dp),
                            label = "Date Of Birth",
                            placeholder = { Text("Select date of birth") },
                            value = profileState.dateOfBirth,
                            trailingIcon = {
                                IconButton(onClick = {
                                    val show =
                                        !profileState.showDatePicker && profileState.inEditMode
                                    viewModel.showDatePicker(show)
                                }) {
                                    Icon(
                                        painter = painterResource(R.drawable.rounded_calendar_month_24),
                                        contentDescription = "Select date of birth"
                                    )
                                }
                            }
                        )

                        if (profileState.showDatePicker) {
                            DatePickerDialog(
                                onDismissRequest = { viewModel.showDatePicker(false) },
                                confirmButton = {
                                    TextButton(onClick = {
                                        viewModel.showDatePicker(false)
                                        viewModel.setDateOfBirth(datePickerState.selectedDateMillis)
                                    }) {
                                        Text("OK")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { viewModel.showDatePicker(false) }) {
                                        Text("Cancel")
                                    }
                                }
                            ) {
                                DatePicker(state = datePickerState)
                            }
                        }
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .width(480.dp)
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    horizontalArrangement = if (profileState.inEditMode) Arrangement.SpaceBetween else Arrangement.Center
                ) {
                    if (profileState.inEditMode) {
                        Button(
                            onClick = {
                                // TODO: Save data
                                viewModel.setEditMode(false)
                                focusManager.clearFocus(true)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SaveButtonColor,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Save")
                        }
                        Button(
                            onClick = {
                                viewModel.setEditMode(false)
                                focusManager.clearFocus(true)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = CancelButtonColor,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Cancel")
                        }
                    } else {
                        Button(
                            onClick = { viewModel.setEditMode(true) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = EditButtonColor,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Edit")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}

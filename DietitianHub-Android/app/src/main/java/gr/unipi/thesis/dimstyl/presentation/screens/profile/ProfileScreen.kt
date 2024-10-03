package gr.unipi.thesis.dimstyl.presentation.screens.profile

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import gr.unipi.thesis.dimstyl.App
import gr.unipi.thesis.dimstyl.R
import gr.unipi.thesis.dimstyl.domain.models.Gender
import gr.unipi.thesis.dimstyl.presentation.components.OutlinedTextField
import gr.unipi.thesis.dimstyl.presentation.components.circularProgressIndicators.ScreenCircularProgressIndicator
import gr.unipi.thesis.dimstyl.presentation.components.dialogs.DatePickerDialog
import gr.unipi.thesis.dimstyl.presentation.theme.BodyColor
import gr.unipi.thesis.dimstyl.presentation.theme.DangerColor
import gr.unipi.thesis.dimstyl.presentation.theme.DataNotFoundColor
import gr.unipi.thesis.dimstyl.presentation.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.presentation.theme.PrimaryColor
import gr.unipi.thesis.dimstyl.presentation.theme.SuccessColor
import gr.unipi.thesis.dimstyl.presentation.theme.TextFieldInputColor
import gr.unipi.thesis.dimstyl.presentation.theme.TopBarColor
import gr.unipi.thesis.dimstyl.presentation.utils.ContentType
import gr.unipi.thesis.dimstyl.presentation.utils.PastSelectableDates
import gr.unipi.thesis.dimstyl.presentation.utils.viewModelFactory
import gr.unipi.thesis.dimstyl.presentation.utils.yearRange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel<ProfileViewModel>(
        factory = viewModelFactory {
            ProfileViewModel(
                App.appModule.fetchProfileDataUseCase,
                App.appModule.updateProfileDataUseCase
            )
        }
    ),
    onSnackbarShow: (String, Boolean) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val profileState by viewModel.state.collectAsStateWithLifecycle()
    val profileData = profileState.profileData

    if (profileState.isLoading) {
        ScreenCircularProgressIndicator()
        LaunchedEffect(Unit) {
            viewModel.fetchProfileData(
                onFetchProfileDataResult = { message, shortDuration ->
                    onSnackbarShow(message, shortDuration)
                }
            )
        }
    } else if (profileData == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Profile data not found",
                fontWeight = FontWeight.SemiBold,
                color = DataNotFoundColor
            )
        }
    } else {
        Column(Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item(contentType = ContentType.PROFILE_TEXT_INPUT_FIELD) {
                    val error = viewModel.validateFirstName(profileData.firstName)
                    viewModel.setHasError(error != null)

                    OutlinedTextField(
                        paddingValues = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp),
                        label = "First Name",
                        placeholder = "Enter first name",
                        value = profileData.firstName,
                        isError = error != null,
                        supportingText = if (error != null) {
                            { Text(error) }
                        } else null,
                        onValueChange = { viewModel.setFirstName(it) },
                        readOnly = !profileState.inEditMode,
                        leadingIcon = {
                            Icon(imageVector = Icons.Rounded.Person, contentDescription = null)
                        },
                        trailingIconVisible = profileData.firstName.isNotEmpty() && profileState.inEditMode,
                        onTrailingIconClick = { viewModel.setFirstName("") },
                        keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) }
                    )
                }

                item(contentType = ContentType.PROFILE_TEXT_INPUT_FIELD) {
                    val error = viewModel.validateLastName(profileData.lastName)
                    viewModel.setHasError(error != null)

                    OutlinedTextField(
                        paddingValues = PaddingValues(horizontal = 16.dp),
                        label = "Last Name",
                        placeholder = "Enter last name",
                        value = profileData.lastName,
                        isError = error != null,
                        supportingText = if (error != null) {
                            { Text(error) }
                        } else null,
                        onValueChange = { viewModel.setLastName(it) },
                        readOnly = !profileState.inEditMode,
                        trailingIconVisible = profileData.lastName.isNotEmpty() && profileState.inEditMode,
                        onTrailingIconClick = { viewModel.setLastName("") },
                        keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) }
                    )
                }

                item(contentType = ContentType.PROFILE_TEXT_INPUT_FIELD) {
                    val error = viewModel.validateEmail(profileData.email)
                    viewModel.setHasError(error != null)

                    OutlinedTextField(
                        paddingValues = PaddingValues(horizontal = 16.dp),
                        label = "Email",
                        placeholder = "Enter email",
                        value = profileData.email,
                        isError = error != null,
                        supportingText = if (error != null) {
                            { Text(error) }
                        } else null,
                        onValueChange = { viewModel.setEmail(it) },
                        readOnly = !profileState.inEditMode,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Email,
                                contentDescription = null
                            )
                        },
                        trailingIconVisible = profileData.email.isNotEmpty() && profileState.inEditMode,
                        onTrailingIconClick = { viewModel.setEmail("") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                            capitalization = KeyboardCapitalization.None
                        ),
                        keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Next) }
                    )
                }

                item(contentType = ContentType.PROFILE_TEXT_INPUT_FIELD) {
                    val error = viewModel.validatePhone(profileData.phone)
                    viewModel.setHasError(error != null)

                    OutlinedTextField(
                        paddingValues = PaddingValues(horizontal = 16.dp),
                        label = "Phone",
                        placeholder = "Enter phone",
                        value = profileData.phone,
                        isError = error != null,
                        supportingText = if (error != null) {
                            { Text(error) }
                        } else null,
                        onValueChange = { viewModel.setPhone(it) },
                        readOnly = !profileState.inEditMode,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.Phone,
                                contentDescription = null
                            )
                        },
                        trailingIconVisible = profileData.phone.isNotEmpty() && profileState.inEditMode,
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

                item(contentType = ContentType.PROFILE_GENDER_DOB_INPUT_FIELDS) {
                    Row(Modifier.width(480.dp)) {
                        ExposedDropdownMenuBox(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .fillMaxWidth(0.45f),
                            expanded = profileState.dropdownExpanded && profileState.inEditMode,
                            onExpandedChange = { viewModel.setDropDownExpanded(it) }
                        ) {
                            OutlinedTextField(
                                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                                label = "Gender",
                                value = profileData.gender.toString(),
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(R.drawable.gender_svgrepo_com),
                                        contentDescription = null
                                    )
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = profileState.dropdownExpanded && profileState.inEditMode
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = TextFieldInputColor,
                                    unfocusedTextColor = TextFieldInputColor,
                                    focusedBorderColor = TopBarColor,
                                    unfocusedBorderColor = LeftBarColor,
                                    focusedLeadingIconColor = TopBarColor,
                                    unfocusedLeadingIconColor = LeftBarColor,
                                    focusedTrailingIconColor = TopBarColor,
                                    unfocusedTrailingIconColor = LeftBarColor,
                                    focusedLabelColor = TopBarColor,
                                    unfocusedLabelColor = LeftBarColor
                                )
                            )

                            ExposedDropdownMenu(
                                containerColor = BodyColor,
                                expanded = profileState.dropdownExpanded && profileState.inEditMode,
                                onDismissRequest = { viewModel.setDropDownExpanded(false) }
                            ) {
                                Gender.entries.toList().forEach {
                                    val textColor =
                                        if (it == profileData.gender) TopBarColor
                                        else TextFieldInputColor

                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = it.toString(),
                                                color = textColor
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
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp, end = 16.dp),
                                label = "Date Of Birth",
                                placeholder = {
                                    Text(
                                        text = "Select date of birth",
                                        textAlign = TextAlign.Center
                                    )
                                },
                                value = profileData.dateOfBirth,
                                trailingIcon = {
                                    IconButton(onClick = {
                                        viewModel.showDatePickerDialog(profileState.inEditMode)
                                    }) {
                                        Icon(
                                            painter = painterResource(R.drawable.rounded_calendar_month_24),
                                            contentDescription = "Select date of birth"
                                        )
                                    }
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = TextFieldInputColor,
                                    unfocusedTextColor = TextFieldInputColor,
                                    focusedBorderColor = TopBarColor,
                                    unfocusedBorderColor = LeftBarColor,
                                    focusedTrailingIconColor = TopBarColor,
                                    unfocusedTrailingIconColor = LeftBarColor,
                                    focusedLabelColor = TopBarColor,
                                    unfocusedLabelColor = LeftBarColor,
                                    focusedPlaceholderColor = TopBarColor
                                )
                            )
                        }
                    }
                }

                item(contentType = ContentType.PROFILE_BUTTONS) {
                    Row(
                        modifier = Modifier
                            .width(480.dp)
                            .padding(horizontal = 16.dp, vertical = 20.dp),
                        horizontalArrangement = if (profileState.inEditMode) Arrangement.SpaceBetween else Arrangement.Center
                    ) {
                        if (profileState.inEditMode) {
                            Button(
                                enabled = profileState.hasError.not(),
                                onClick = {
                                    viewModel.saveProfileData(
                                        onSaveProfileDataResult = { message, shortDuration ->
                                            focusManager.clearFocus(true)
                                            onSnackbarShow(message, shortDuration)
                                        }
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SuccessColor,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Save")
                            }
                            Button(
                                onClick = {
                                    viewModel.restoreProfileData()
                                    viewModel.setEditMode(false)
                                    focusManager.clearFocus(true)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DangerColor.copy(alpha = 0.8f),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Cancel")
                            }
                        } else {
                            Button(
                                onClick = {
                                    viewModel.backupProfileData()
                                    viewModel.setEditMode(true)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PrimaryColor,
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

    if (profileState.showDatePickerDialog) {
        val datePickerState = DatePickerState(
            locale = LocalContext.current.resources.configuration.locales[0],
            initialSelectedDateMillis = profileState.dateOfBirthMillis,
            yearRange = yearRange(inPast = true),
            selectableDates = PastSelectableDates
        )

        DatePickerDialog(
            onDismiss = { viewModel.showDatePickerDialog(false) },
            onConfirm = {
                viewModel.showDatePickerDialog(false)
                val selectedDateMillis = datePickerState.selectedDateMillis ?: 0L
                viewModel.setDateOfBirth(selectedDateMillis)
            },
            content = { DatePicker(state = datePickerState) }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen { _, _ -> }
}

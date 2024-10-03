package gr.unipi.thesis.dimstyl.presentation.screens.profile

import gr.unipi.thesis.dimstyl.data.models.ProfileData

data class ProfileState(
    val profileData: ProfileData? = null,
    val oldProfileData: ProfileData? = null,
    val dropdownExpanded: Boolean = false,
    val showDatePickerDialog: Boolean = false,
    val inEditMode: Boolean = false,
    val dateOfBirthMillis: Long = 0,
    val hasError: Boolean = false,
    val isLoading: Boolean = true
)
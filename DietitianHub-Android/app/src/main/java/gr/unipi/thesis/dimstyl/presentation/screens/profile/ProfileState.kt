package gr.unipi.thesis.dimstyl.presentation.screens.profile

import gr.unipi.thesis.dimstyl.data.models.ProfileData
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

data class ProfileState(
    val profileData: ProfileData? = null,
    val oldProfileData: ProfileData? = null,
    val dropdownExpanded: Boolean = false,
    val showDatePickerDialog: Boolean = false,
    val inEditMode: Boolean = false,
    val isLoading: Boolean = false
) {

    val dateOfBirthMillis: Long?
        get() {
            val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            return formatter.parse(profileData?.dateOfBirth ?: return null)?.time ?: return null
        }

}
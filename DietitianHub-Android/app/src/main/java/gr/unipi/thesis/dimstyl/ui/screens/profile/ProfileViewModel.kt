package gr.unipi.thesis.dimstyl.ui.screens.profile

import androidx.lifecycle.ViewModel
import gr.unipi.thesis.dimstyl.data.model.Gender
import gr.unipi.thesis.dimstyl.data.model.ProfileData
import gr.unipi.thesis.dimstyl.ui.helpers.convertMillisToDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        fetchProfileData()
    }

    private fun fetchProfileData() {
        _state.value = _state.value.copy(isLoading = true)

        // TODO: Fetch appointments from the server
        val profileData = ProfileData(
            firstName = "John",
            lastName = "Doe",
            email = "johDoe@email.com",
            phone = "1234567890",
            gender = Gender.MALE,
            dateOfBirth = "28/01/1990"
        )

        _state.value = _state.value.copy(profileData = profileData, isLoading = false)
    }

    fun setFirstName(firstName: String) {
        val profileData = _state.value.profileData?.copy(firstName = firstName)
        _state.value = _state.value.copy(profileData = profileData)
    }

    fun setLastName(lastName: String) {
        val profileData = _state.value.profileData?.copy(lastName = lastName)
        _state.value = _state.value.copy(profileData = profileData)
    }

    fun setEmail(email: String) {
        val profileData = _state.value.profileData?.copy(email = email)
        _state.value = _state.value.copy(profileData = profileData)
    }

    fun setPhone(phone: String) {
        val profileData = _state.value.profileData?.copy(phone = phone)
        _state.value = _state.value.copy(profileData = profileData)
    }

    fun setDateOfBirth(date: Long?) {
        if (date == null) return
        val profileData = _state.value.profileData?.copy(dateOfBirth = convertMillisToDate(date))
        _state.value = _state.value.copy(profileData = profileData)
    }

    fun setGender(gender: Gender) {
        val profileData = _state.value.profileData?.copy(gender = gender)
        _state.value = _state.value.copy(profileData = profileData)
    }

    fun setDropDownExpanded(expanded: Boolean) {
        _state.value = _state.value.copy(dropdownExpanded = expanded)
    }

    fun setEditMode(editMode: Boolean) {
        _state.value = _state.value.copy(inEditMode = editMode)
    }

    fun showDatePickerDialog(show: Boolean) {
        _state.value = _state.value.copy(showDatePickerDialog = show)
    }

    fun backupProfileData() {
        _state.value = _state.value.copy(oldProfileData = _state.value.profileData)
    }

    fun restoreProfileData() {
        _state.value = _state.value.copy(profileData = _state.value.oldProfileData)
    }

}
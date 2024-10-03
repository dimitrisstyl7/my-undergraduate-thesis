package gr.unipi.thesis.dimstyl.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.unipi.thesis.dimstyl.domain.models.Gender
import gr.unipi.thesis.dimstyl.domain.usecases.FetchProfileDataUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.UpdateProfileDataUseCase
import gr.unipi.thesis.dimstyl.presentation.utils.getDateMillis
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.FETCH_PROFILE_DATA_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.UPDATE_PROFILE_DATA_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.SuccessMessages.PROFILE_DATA_UPDATED_SUCCESS_MESSAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class ProfileViewModel(
    private val fetchProfileDataUseCase: FetchProfileDataUseCase,
    private val updateProfileDataUseCase: UpdateProfileDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    fun fetchProfileData(onFetchProfileDataResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val result = fetchProfileDataUseCase()
            val profileData = result.getOrNull()

            if (result.isSuccess && profileData != null) {
                val dateOfBirthMillis = getDateMillis(profileData.dateOfBirth)
                _state.value = _state.value.copy(
                    profileData = profileData,
                    dateOfBirthMillis = dateOfBirthMillis,
                    isLoading = false
                )
            } else {
                val errorMessage =
                    result.exceptionOrNull()?.message ?: FETCH_PROFILE_DATA_ERROR_MESSAGE
                onFetchProfileDataResult(errorMessage, false)
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    fun saveProfileData(onSaveProfileDataResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            val profileData = _state.value.profileData
            var message = UPDATE_PROFILE_DATA_ERROR_MESSAGE
            var shortDuration = false

            if (profileData != null) {
                val result = updateProfileDataUseCase(profileData)

                if (result.isSuccess) {
                    shortDuration = true
                    message = result.getOrNull() ?: PROFILE_DATA_UPDATED_SUCCESS_MESSAGE
                    val dateOfBirthMillis = getDateMillis(profileData.dateOfBirth)
                    _state.value = _state.value.copy(
                        oldProfileData = profileData,
                        dateOfBirthMillis = dateOfBirthMillis
                    )
                } else {
                    _state.value = _state.value.copy(profileData = _state.value.oldProfileData)
                    message = result.exceptionOrNull()?.message ?: message
                }
            }

            onSaveProfileDataResult(message, shortDuration)
            setEditMode(false)
        }
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

    fun setDateOfBirth(selectedDateMillis: Long) {
        val selectedDate =
            Instant.ofEpochMilli(selectedDateMillis).atZone(ZoneId.systemDefault()).toLocalDate()
        val profileData = _state.value.profileData?.copy(dateOfBirth = selectedDate.toString())
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

    fun setHasError(hasError: Boolean) {
        _state.value = _state.value.copy(hasError = hasError)
    }

    fun validateFirstName(text: String): String? {
        return if (text.isBlank()) "First name cannot be empty"
        else if (text.length < 2 || text.length > 50) "First name must be between 2 and 50 characters"
        else if (!text.matches(Regex("^[a-zA-Z]+\$"))) "First name must contain only letters"
        else null
    }

    fun validateLastName(text: String): String? {
        return if (text.isBlank()) "Last name cannot be empty"
        else if (text.length < 2 || text.length > 50) "Last name must be between 2 and 50 characters"
        else if (!text.matches(Regex("^[a-zA-Z]+\$"))) "Last name must contain only letters"
        else null
    }

    fun validateEmail(text: String): String? {
        return if (text.isBlank()) "Email cannot be empty"
        else if (android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) null
        else "Invalid email format"
    }

    fun validatePhone(text: String): String? {
        return if (text.isBlank()) "Phone number cannot be empty"
        else if (text.length < 8 || text.length > 20) "Phone number must be between 8 and 20 digits (including '+')"
        else if (text.matches(Regex("^[+]?[0-9]{8,19}\$"))
                .not()
        ) "Phone number can start with '+' and be followed by 8-19 digits"
        else null
    }

}
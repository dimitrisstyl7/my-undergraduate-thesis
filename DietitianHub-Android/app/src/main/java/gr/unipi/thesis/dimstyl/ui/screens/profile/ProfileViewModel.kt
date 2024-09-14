package gr.unipi.thesis.dimstyl.ui.screens.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileViewModel : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    fun setFirstName(firstName: String) {
        _state.value = _state.value.copy(firstName = firstName)
    }

    fun setLastName(lastName: String) {
        _state.value = _state.value.copy(lastName = lastName)
    }

    fun setEmail(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun setPhone(phone: String) {
        _state.value = _state.value.copy(phone = phone)
    }

    fun setDateOfBirth(date: Long?) {
        if (date != null) _state.value = _state.value.copy(dateOfBirth = convertMillisToDate(date))
    }

    fun setGender(gender: Gender) {
        _state.value = _state.value.copy(gender = gender)
    }

    fun setDropDownExpanded(expanded: Boolean) {
        _state.value = _state.value.copy(dropdownExpanded = expanded)
    }

    fun setEditMode(editMode: Boolean) {
        _state.value = _state.value.copy(inEditMode = editMode)
    }

    fun showDatePicker(show: Boolean) {
        _state.value = _state.value.copy(showDatePicker = show)
    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }

}
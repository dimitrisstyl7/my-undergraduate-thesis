package gr.unipi.thesis.dimstyl.ui.screens.profile

import java.text.SimpleDateFormat
import java.util.Locale

data class ProfileState(
    val firstName: String = "Dummy", // TODO: init value coming from repository
    val lastName: String = "Data", // TODO: init value coming from repository
    val email: String = "dummydata@email.com", // TODO: init value coming from repository
    val phone: String = "1223483493", // TODO: init value coming from repository
    val dateOfBirth: String = "01/29/2001", // TODO: init value coming from repository
    val gender: Gender = Gender.MALE, // TODO: init value coming from repository
    val dropdownExpanded: Boolean = false,
    val showDatePicker: Boolean = false,
    val inEditMode: Boolean = false
) {

    val dateOfBirthMillis: Long
        get() {
            val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            return formatter.parse(dateOfBirth)?.time ?: 0
        }

}

enum class Gender {

    MALE, FEMALE;

    override fun toString(): String {
        return this.name[0] + this.name.substring(1).lowercase()
    }

}
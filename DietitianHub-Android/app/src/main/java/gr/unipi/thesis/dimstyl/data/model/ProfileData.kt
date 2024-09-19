package gr.unipi.thesis.dimstyl.data.model

data class ProfileData(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val gender: Gender,
    val dateOfBirth: String
)
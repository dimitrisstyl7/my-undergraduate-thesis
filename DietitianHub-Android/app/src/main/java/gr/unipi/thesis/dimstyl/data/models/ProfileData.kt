package gr.unipi.thesis.dimstyl.data.models

import gr.unipi.thesis.dimstyl.domain.models.Gender

data class ProfileData(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val gender: Gender,
    val dateOfBirth: String
)
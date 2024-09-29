package gr.unipi.thesis.dimstyl.data.models

import gr.unipi.thesis.dimstyl.domain.models.AppointmentStatus

data class Appointment(val id: Int, val start: String, val status: AppointmentStatus)

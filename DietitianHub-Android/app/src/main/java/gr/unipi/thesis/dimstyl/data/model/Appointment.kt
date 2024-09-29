package gr.unipi.thesis.dimstyl.data.model

import gr.unipi.thesis.dimstyl.domain.model.AppointmentStatus

data class Appointment(val id: Int, val start: String, val status: AppointmentStatus)

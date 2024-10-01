package gr.unipi.thesis.dimstyl.domain.repositories

import gr.unipi.thesis.dimstyl.data.models.Appointment
import gr.unipi.thesis.dimstyl.data.models.AppointmentsResponse

interface AppointmentRepository {

    suspend fun fetchAppointments(): Result<AppointmentsResponse>
    suspend fun createAppointment(appointment: Appointment): Result<Appointment>
    suspend fun cancelAppointment(id: Int): Result<Unit>

}
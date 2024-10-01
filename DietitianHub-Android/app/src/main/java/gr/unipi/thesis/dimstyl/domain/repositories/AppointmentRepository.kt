package gr.unipi.thesis.dimstyl.domain.repositories

interface AppointmentRepository {

    suspend fun cancelAppointment(id: Int): Result<Unit>

}
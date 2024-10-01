package gr.unipi.thesis.dimstyl.domain.usecases

import gr.unipi.thesis.dimstyl.domain.repositories.AppointmentRepository

class FetchAppointmentsUseCase(private val appointmentRepository: AppointmentRepository) {

    suspend fun execute() = appointmentRepository.fetchAppointments()

}
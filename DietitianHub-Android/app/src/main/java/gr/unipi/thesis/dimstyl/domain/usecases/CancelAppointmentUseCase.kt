package gr.unipi.thesis.dimstyl.domain.usecases

import gr.unipi.thesis.dimstyl.domain.repositories.AppointmentRepository

class CancelAppointmentUseCase(private val appointmentRepository: AppointmentRepository) {

    suspend operator fun invoke(id: Int) = appointmentRepository.cancelAppointment(id)

}
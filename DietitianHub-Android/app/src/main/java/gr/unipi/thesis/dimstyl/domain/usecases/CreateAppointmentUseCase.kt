package gr.unipi.thesis.dimstyl.domain.usecases

import gr.unipi.thesis.dimstyl.data.models.Appointment
import gr.unipi.thesis.dimstyl.domain.repositories.AppointmentRepository
import java.time.LocalDateTime

class CreateAppointmentUseCase(private val appointmentRepository: AppointmentRepository) {

    suspend operator fun invoke(requestedDateTime: LocalDateTime): Result<Appointment> {
        return appointmentRepository.createAppointment(
            Appointment(appointmentDateTime = requestedDateTime.toString())
        )
    }

}
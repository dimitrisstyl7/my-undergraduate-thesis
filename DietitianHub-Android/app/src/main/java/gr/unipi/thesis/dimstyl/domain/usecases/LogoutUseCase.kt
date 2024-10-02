package gr.unipi.thesis.dimstyl.domain.usecases

import gr.unipi.thesis.dimstyl.domain.repositories.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): Result<String> = authRepository.logout()

}
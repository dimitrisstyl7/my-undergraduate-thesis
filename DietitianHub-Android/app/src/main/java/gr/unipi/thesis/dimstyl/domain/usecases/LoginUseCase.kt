package gr.unipi.thesis.dimstyl.domain.usecases

import gr.unipi.thesis.dimstyl.data.models.LoginRequest
import gr.unipi.thesis.dimstyl.domain.repositories.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {

    suspend fun execute(email: String, password: String): Result<Unit> {
        return authRepository.login(LoginRequest(email, password))
    }

}
package gr.unipi.thesis.dimstyl.domain.usecase

import gr.unipi.thesis.dimstyl.data.model.LoginRequest
import gr.unipi.thesis.dimstyl.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {

    suspend fun execute(email: String, password: String): Result<String> {
        return authRepository.login(LoginRequest(email, password))
    }

}
package gr.unipi.thesis.dimstyl.domain.usecases

import gr.unipi.thesis.dimstyl.data.utils.TokenUtils
import gr.unipi.thesis.dimstyl.domain.repositories.AuthRepository
import gr.unipi.thesis.dimstyl.exceptions.JwtAccessTokenExpired
import gr.unipi.thesis.dimstyl.exceptions.UnknownException

class CheckTokenValidityUseCase(private val authRepository: AuthRepository) {

    suspend fun execute(): Result<Unit> {
        val result = authRepository.retrieveAccessToken()
        val token = result.getOrNull()

        if (result.isFailure || token == null) return Result.failure(
            result.exceptionOrNull() ?: UnknownException()
        )
        if (TokenUtils.isTokenExpired(token)) return Result.failure(JwtAccessTokenExpired())
        return Result.success(Unit)
    }

}
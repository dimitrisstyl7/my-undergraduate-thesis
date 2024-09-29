package gr.unipi.thesis.dimstyl.data.repository

import android.util.Log
import gr.unipi.thesis.dimstyl.data.model.LoginRequest
import gr.unipi.thesis.dimstyl.data.model.LoginResponse
import gr.unipi.thesis.dimstyl.data.source.local.JwtTokenManager
import gr.unipi.thesis.dimstyl.data.source.remote.AuthApiService
import gr.unipi.thesis.dimstyl.data.util.ErrorMessages
import gr.unipi.thesis.dimstyl.data.util.ErrorParser
import gr.unipi.thesis.dimstyl.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val jwtTokenManager: JwtTokenManager
) : AuthRepository {

    companion object {
        private const val TAG = "AuthRepositoryImpl"
        private const val DEFAULT_ERROR_MESSAGE = ErrorMessages.DEFAULT_LOGIN_ERROR_MESSAGE
    }

    override suspend fun login(loginRequest: LoginRequest): Result<String> {
        return withContext(Dispatchers.IO) {
            val response: Response<LoginResponse>

            try {
                response = authApiService.login(loginRequest)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to login: ${e.message}")
                return@withContext Result.failure(Exception(DEFAULT_ERROR_MESSAGE))
            }

            if (response.isSuccessful) {
                val body: LoginResponse? = response.body()

                when {
                    body == null || body.token.isBlank() -> {
                        Log.e(TAG, "Failed to login: Response body is null or token is empty")
                        Result.failure(Exception(DEFAULT_ERROR_MESSAGE))
                    }

                    else -> saveAccessToken(body.token)
                }
            } else {
                val errorMessage = handleErrorResponse(response.errorBody()?.string())
                Log.e(TAG, "Failed to login: $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        }
    }

    private suspend fun saveAccessToken(accessToken: String): Result<String> {
        return try {
            jwtTokenManager.saveAccessToken(accessToken)
            Result.success(accessToken)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save token: ${e.message}")
            Result.failure(Exception(DEFAULT_ERROR_MESSAGE))
        }
    }

    private fun handleErrorResponse(errorBody: String?): String {
        return errorBody?.let {
            ErrorParser.parseResponseErrorBody(it, DEFAULT_ERROR_MESSAGE)
        } ?: DEFAULT_ERROR_MESSAGE
    }

}
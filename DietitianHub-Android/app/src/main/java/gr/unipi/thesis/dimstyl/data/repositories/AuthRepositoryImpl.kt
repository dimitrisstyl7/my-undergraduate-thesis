package gr.unipi.thesis.dimstyl.data.repositories

import android.util.Log
import gr.unipi.thesis.dimstyl.data.models.LoginRequest
import gr.unipi.thesis.dimstyl.data.models.LoginResponse
import gr.unipi.thesis.dimstyl.data.sources.local.JwtTokenManager
import gr.unipi.thesis.dimstyl.data.sources.remote.AuthApiService
import gr.unipi.thesis.dimstyl.data.utils.ErrorParser
import gr.unipi.thesis.dimstyl.domain.repositories.AuthRepository
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.DEFAULT_LOGIN_ERROR_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val jwtTokenManager: JwtTokenManager
) : AuthRepository {

    companion object {
        private const val TAG = "AuthRepositoryImpl"
    }

    override suspend fun login(loginRequest: LoginRequest): Result<String> {
        return withContext(Dispatchers.IO) {
            val response: Response<LoginResponse>

            try {
                response = authApiService.login(loginRequest)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to login: ${e.message}")
                return@withContext Result.failure(Exception(DEFAULT_LOGIN_ERROR_MESSAGE))
            }

            if (response.isSuccessful) {
                val body: LoginResponse? = response.body()

                when {
                    body == null || body.token.isBlank() -> {
                        Log.e(TAG, "Failed to login: Response body is null or token is empty")
                        return@withContext Result.failure(Exception(DEFAULT_LOGIN_ERROR_MESSAGE))
                    }

                    else -> return@withContext saveAccessToken(body.token)
                }
            } else {
                val errorMessage = handleErrorResponse(response.errorBody()?.string())
                Log.e(TAG, "Failed to login: $errorMessage")
                return@withContext Result.failure(Exception(errorMessage))
            }
        }
    }

    private suspend fun saveAccessToken(accessToken: String): Result<String> {
        return try {
            jwtTokenManager.saveAccessToken(accessToken)
            Result.success(accessToken)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save token: ${e.message}")
            Result.failure(Exception(DEFAULT_LOGIN_ERROR_MESSAGE))
        }
    }

    private fun handleErrorResponse(errorBody: String?): String {
        return errorBody?.let {
            ErrorParser.parseResponseErrorBody(it, DEFAULT_LOGIN_ERROR_MESSAGE)
        } ?: DEFAULT_LOGIN_ERROR_MESSAGE
    }

}
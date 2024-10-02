package gr.unipi.thesis.dimstyl.data.repositories

import android.util.Log
import gr.unipi.thesis.dimstyl.data.models.LoginRequest
import gr.unipi.thesis.dimstyl.data.models.LoginResponse
import gr.unipi.thesis.dimstyl.data.sources.local.JwtTokenManager
import gr.unipi.thesis.dimstyl.data.sources.remote.services.AuthApiService
import gr.unipi.thesis.dimstyl.data.utils.ErrorParser.extractErrorMessage
import gr.unipi.thesis.dimstyl.domain.repositories.AuthRepository
import gr.unipi.thesis.dimstyl.exceptions.JwtAccessTokenDoesNotExist
import gr.unipi.thesis.dimstyl.exceptions.JwtAccessTokenRetrievalFailed
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.LOGIN_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.LOGOUT_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.SuccessMessages.LOGOUT_SUCCESS_MESSAGE
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

    override suspend fun login(loginRequest: LoginRequest): Result<Unit> {
        return withContext(Dispatchers.IO) {
            val response: Response<LoginResponse>

            try {
                response = authApiService.login(loginRequest)
            } catch (e: Exception) {
                Log.e(TAG, "Login failed", e)
                return@withContext Result.failure(Exception(LOGIN_ERROR_MESSAGE))
            }

            if (response.isSuccessful) {
                val body: LoginResponse? = response.body()

                when {
                    body == null || body.token.isBlank() -> {
                        Log.e(TAG, "Login failed: Response body is null or token is empty")
                        return@withContext Result.failure(Exception(LOGIN_ERROR_MESSAGE))
                    }

                    else -> return@withContext saveAccessToken(body.token)
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = extractErrorMessage(errorBody, LOGIN_ERROR_MESSAGE)
                Log.e(TAG, "Login failed: $errorMessage")
                return@withContext Result.failure(Exception(errorMessage))
            }
        }
    }

    override suspend fun retrieveAccessToken(): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val token = jwtTokenManager.getAccessToken()
                if (token == null) {
                    Log.d(TAG, "Token does not exist")
                    return@withContext Result.failure(JwtAccessTokenDoesNotExist())
                }
                return@withContext Result.success(token)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to retrieve token", e)
                return@withContext Result.failure(JwtAccessTokenRetrievalFailed())
            }
        }
    }

    override suspend fun logout(): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                jwtTokenManager.clearAccessToken()
                return@withContext Result.success(LOGOUT_SUCCESS_MESSAGE)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to clear token", e)
                return@withContext Result.failure(Exception(LOGOUT_ERROR_MESSAGE))
            }
        }
    }

    private suspend fun saveAccessToken(accessToken: String): Result<Unit> {
        return try {
            jwtTokenManager.saveAccessToken(accessToken)
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save token", e)
            Result.failure(Exception(LOGIN_ERROR_MESSAGE))
        }
    }

}
package gr.unipi.thesis.dimstyl.data.repositories

import android.util.Log
import gr.unipi.thesis.dimstyl.data.models.ProfileData
import gr.unipi.thesis.dimstyl.data.sources.remote.services.ProfileApiService
import gr.unipi.thesis.dimstyl.domain.repositories.ProfileRepository
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.FETCH_PROFILE_DATA_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.UPDATE_PROFILE_DATA_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.SuccessMessages.PROFILE_DATA_UPDATED_SUCCESS_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ProfileRepositoryImpl(private val profileApiService: ProfileApiService) : ProfileRepository {

    companion object {
        private const val TAG = "ProfileRepositoryImpl"
    }

    override suspend fun fetchProfileData(): Result<ProfileData> {
        return withContext(Dispatchers.IO) {
            val response: Response<ProfileData>

            try {
                response = profileApiService.fetchProfileData()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch profile data", e)
                return@withContext Result.failure(Exception(FETCH_PROFILE_DATA_ERROR_MESSAGE))
            }

            if (response.isSuccessful) {
                val body: ProfileData? = response.body()

                when {
                    body == null -> {
                        Log.e(TAG, "Failed to fetch profile data: Response body is null")
                        return@withContext Result.failure(Exception(FETCH_PROFILE_DATA_ERROR_MESSAGE))
                    }

                    else -> return@withContext Result.success(body)
                }
            } else {
                Log.e(TAG, "Failed to fetch profile data: ${response.errorBody()?.string()}")
                return@withContext Result.failure(Exception(FETCH_PROFILE_DATA_ERROR_MESSAGE))
            }
        }
    }

    override suspend fun updateProfileData(profileData: ProfileData): Result<String> {
        return withContext(Dispatchers.IO) {
            val response: Response<Unit>

            try {
                response = profileApiService.updateProfileData(profileData)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update profile data", e)
                return@withContext Result.failure(Exception(UPDATE_PROFILE_DATA_ERROR_MESSAGE))
            }

            if (response.isSuccessful) {
                return@withContext Result.success(PROFILE_DATA_UPDATED_SUCCESS_MESSAGE)
            } else {
                Log.e(TAG, "Failed to update profile data: ${response.errorBody()?.string()}")
                return@withContext Result.failure(Exception(UPDATE_PROFILE_DATA_ERROR_MESSAGE))
            }
        }
    }

}
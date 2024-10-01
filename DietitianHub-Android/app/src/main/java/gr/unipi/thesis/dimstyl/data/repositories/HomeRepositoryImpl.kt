package gr.unipi.thesis.dimstyl.data.repositories

import android.util.Log
import gr.unipi.thesis.dimstyl.data.models.HomeResponse
import gr.unipi.thesis.dimstyl.data.sources.remote.services.HomeApiService
import gr.unipi.thesis.dimstyl.domain.repositories.HomeRepository
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.FETCH_DATA_ERROR_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class HomeRepositoryImpl(private val homeApiService: HomeApiService) : HomeRepository {

    companion object {
        private const val TAG = "HomeRepositoryImpl"
    }

    override suspend fun fetchHomeData(): Result<HomeResponse> {
        return withContext(Dispatchers.IO) {
            val response: Response<HomeResponse>

            try {
                response = homeApiService.fetchHomeData()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch home data", e)
                return@withContext Result.failure(Exception(FETCH_DATA_ERROR_MESSAGE))
            }

            if (response.isSuccessful) {
                val body: HomeResponse? = response.body()

                when {
                    body == null -> {
                        Log.e(TAG, "Failed to fetch home data: Response body is null")
                        return@withContext Result.failure(Exception(FETCH_DATA_ERROR_MESSAGE))
                    }

                    else -> return@withContext Result.success(body)
                }
            } else {
                Log.e(TAG, "Failed to fetch home data: ${response.errorBody()?.string()}")
                return@withContext Result.failure(Exception(FETCH_DATA_ERROR_MESSAGE))
            }
        }
    }
}
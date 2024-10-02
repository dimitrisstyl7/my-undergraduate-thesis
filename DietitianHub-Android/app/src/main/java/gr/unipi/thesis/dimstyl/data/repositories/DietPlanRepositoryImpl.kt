package gr.unipi.thesis.dimstyl.data.repositories

import android.app.DownloadManager
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import gr.unipi.thesis.dimstyl.data.models.DietPlan
import gr.unipi.thesis.dimstyl.data.sources.local.JwtTokenManager
import gr.unipi.thesis.dimstyl.data.sources.remote.services.DietPlanApiService
import gr.unipi.thesis.dimstyl.domain.repositories.DietPlanRepository
import gr.unipi.thesis.dimstyl.utils.Constants.Authorization.HEADER_AUTHORIZATION
import gr.unipi.thesis.dimstyl.utils.Constants.Authorization.TOKEN_TYPE
import gr.unipi.thesis.dimstyl.utils.Constants.BaseUrl.LOCALHOST
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.FETCH_DIET_PLANS_ERROR_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Response

class DietPlanRepositoryImpl(
    private val dietPlanApiService: DietPlanApiService,
    private val downloadManager: DownloadManager,
    private val jwtTokenManager: JwtTokenManager
) :
    DietPlanRepository {

    companion object {
        private const val TAG = "DietPlanRepositoryImpl"
    }

    override suspend fun fetchDietPlans(): Result<List<DietPlan>> {
        return withContext(Dispatchers.IO) {
            val response: Response<List<DietPlan>>

            try {
                response = dietPlanApiService.fetchDietPlans()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch diet plans", e)
                return@withContext Result.failure(Exception(FETCH_DIET_PLANS_ERROR_MESSAGE))
            }

            if (response.isSuccessful) {
                val body: List<DietPlan>? = response.body()

                when {
                    body == null -> {
                        Log.e(TAG, "Failed to fetch diet plans: Response body is null")
                        return@withContext Result.failure(Exception(FETCH_DIET_PLANS_ERROR_MESSAGE))
                    }

                    else -> return@withContext Result.success(body)
                }
            } else {
                Log.e(TAG, "Failed to fetch articles: ${response.errorBody()?.string()}")
                return@withContext Result.failure(Exception(FETCH_DIET_PLANS_ERROR_MESSAGE))
            }
        }
    }

    override fun downloadDietPlan(id: Int, fileName: String) {
        val token = runBlocking { jwtTokenManager.getAccessToken() }
        val request = DownloadManager.Request("${LOCALHOST}dietPlans/$id".toUri())
            .addRequestHeader(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
            .setMimeType("application/pdf")
            .setTitle(fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        val id = downloadManager.enqueue(request)
    }

}
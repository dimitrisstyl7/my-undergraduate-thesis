package gr.unipi.thesis.dimstyl.data.repositories

import android.util.Log
import gr.unipi.thesis.dimstyl.data.models.AnnouncementsResponse
import gr.unipi.thesis.dimstyl.data.sources.remote.services.AnnouncementApiService
import gr.unipi.thesis.dimstyl.domain.models.AnnouncementSection
import gr.unipi.thesis.dimstyl.domain.repositories.AnnouncementRepository
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.FETCH_ANNOUNCEMENTS_ERROR_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AnnouncementRepositoryImpl(private val announcementApiService: AnnouncementApiService) :
    AnnouncementRepository {

    companion object {
        private const val TAG = "AnnouncementRepositoryImpl"
    }

    override suspend fun fetchAnnouncements(): Result<List<AnnouncementSection>> {
        return withContext(Dispatchers.IO) {
            val response: Response<AnnouncementsResponse>

            try {
                response = announcementApiService.fetchAnnouncements()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch announcements", e)
                return@withContext Result.failure(Exception(FETCH_ANNOUNCEMENTS_ERROR_MESSAGE))
            }

            if (response.isSuccessful) {
                val body: AnnouncementsResponse? = response.body()

                when {
                    body == null -> {
                        Log.e(TAG, "Failed to fetch announcements: Response body is null")
                        return@withContext Result.failure(
                            Exception(FETCH_ANNOUNCEMENTS_ERROR_MESSAGE)
                        )
                    }

                    else -> return@withContext Result.success(body.toAnnouncementSections())
                }
            } else {
                Log.e(TAG, "Failed to fetch announcements: ${response.errorBody()?.string()}")
                return@withContext Result.failure(Exception(FETCH_ANNOUNCEMENTS_ERROR_MESSAGE))
            }
        }
    }

}
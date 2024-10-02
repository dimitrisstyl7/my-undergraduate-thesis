package gr.unipi.thesis.dimstyl.data.sources.remote.services

import gr.unipi.thesis.dimstyl.data.models.AnnouncementsResponse
import retrofit2.Response
import retrofit2.http.GET

interface AnnouncementApiService {

    @GET("announcements")
    suspend fun fetchAnnouncements(): Response<AnnouncementsResponse>

}
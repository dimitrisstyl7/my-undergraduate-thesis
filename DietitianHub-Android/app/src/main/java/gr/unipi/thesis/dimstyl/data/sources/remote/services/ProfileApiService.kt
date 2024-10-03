package gr.unipi.thesis.dimstyl.data.sources.remote.services

import gr.unipi.thesis.dimstyl.data.models.ProfileData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileApiService {

    @GET("profile")
    suspend fun fetchProfileData(): Response<ProfileData>

    @PUT("profile")
    suspend fun updateProfileData(@Body profileData: ProfileData): Response<Unit>

}
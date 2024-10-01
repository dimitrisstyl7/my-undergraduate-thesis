package gr.unipi.thesis.dimstyl.data.sources.remote.services

import gr.unipi.thesis.dimstyl.data.models.HomeResponse
import retrofit2.Response
import retrofit2.http.GET

interface HomeApiService {

    @GET("home")
    suspend fun fetchHomeData(): Response<HomeResponse>

}
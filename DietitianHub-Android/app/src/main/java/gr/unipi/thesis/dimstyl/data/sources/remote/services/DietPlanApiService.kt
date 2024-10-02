package gr.unipi.thesis.dimstyl.data.sources.remote.services

import gr.unipi.thesis.dimstyl.data.models.DietPlan
import retrofit2.Response
import retrofit2.http.GET

interface DietPlanApiService {

    @GET("dietPlans")
    suspend fun fetchDietPlans(): Response<List<DietPlan>>

}
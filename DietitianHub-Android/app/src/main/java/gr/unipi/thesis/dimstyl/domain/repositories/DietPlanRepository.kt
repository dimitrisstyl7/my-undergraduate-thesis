package gr.unipi.thesis.dimstyl.domain.repositories

import gr.unipi.thesis.dimstyl.data.models.DietPlan

interface DietPlanRepository {

    suspend fun fetchDietPlans(): Result<List<DietPlan>>
    fun downloadDietPlan(id: Int, fileName: String)

}
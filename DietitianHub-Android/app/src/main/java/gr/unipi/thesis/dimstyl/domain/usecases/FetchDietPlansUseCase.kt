package gr.unipi.thesis.dimstyl.domain.usecases

import gr.unipi.thesis.dimstyl.domain.repositories.DietPlanRepository

class FetchDietPlansUseCase(private val repository: DietPlanRepository) {

    suspend operator fun invoke() = repository.fetchDietPlans()

}
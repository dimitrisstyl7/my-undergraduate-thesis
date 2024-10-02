package gr.unipi.thesis.dimstyl.domain.usecases

import gr.unipi.thesis.dimstyl.domain.repositories.DietPlanRepository

class DownloadDietPlanUseCase(private val dietPlanRepository: DietPlanRepository) {

    operator fun invoke(id: Int, fileName: String) {
        dietPlanRepository.downloadDietPlan(id, fileName)
    }

}
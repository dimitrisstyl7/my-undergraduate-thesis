package gr.unipi.thesis.dimstyl.domain.usecases

import gr.unipi.thesis.dimstyl.domain.repositories.HomeRepository

class FetchHomeDataUseCase(private val homeRepository: HomeRepository) {

    suspend operator fun invoke() = homeRepository.fetchHomeData()

}
package gr.unipi.thesis.dimstyl.domain.usecases

import gr.unipi.thesis.dimstyl.domain.repositories.ProfileRepository

class FetchProfileDataUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke() = profileRepository.fetchProfileData()

}
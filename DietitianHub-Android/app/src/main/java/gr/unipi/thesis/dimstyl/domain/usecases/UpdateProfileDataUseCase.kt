package gr.unipi.thesis.dimstyl.domain.usecases

import gr.unipi.thesis.dimstyl.data.models.ProfileData
import gr.unipi.thesis.dimstyl.domain.repositories.ProfileRepository

class UpdateProfileDataUseCase(private val profileRepository: ProfileRepository) {

    suspend operator fun invoke(profileData: ProfileData): Result<String> {
        return profileRepository.updateProfileData(profileData)
    }

}
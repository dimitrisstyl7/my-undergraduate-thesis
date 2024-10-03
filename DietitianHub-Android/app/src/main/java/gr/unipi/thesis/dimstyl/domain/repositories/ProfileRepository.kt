package gr.unipi.thesis.dimstyl.domain.repositories

import gr.unipi.thesis.dimstyl.data.models.ProfileData

interface ProfileRepository {

    suspend fun fetchProfileData(): Result<ProfileData>
    suspend fun updateProfileData(profileData: ProfileData): Result<String>

}
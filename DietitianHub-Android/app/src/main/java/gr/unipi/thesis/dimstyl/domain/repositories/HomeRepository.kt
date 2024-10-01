package gr.unipi.thesis.dimstyl.domain.repositories

import gr.unipi.thesis.dimstyl.data.models.HomeResponse

interface HomeRepository {

    suspend fun fetchHomeData(): Result<HomeResponse>

}
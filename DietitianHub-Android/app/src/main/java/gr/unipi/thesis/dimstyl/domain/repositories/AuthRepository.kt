package gr.unipi.thesis.dimstyl.domain.repositories

import gr.unipi.thesis.dimstyl.data.models.LoginRequest

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest): Result<String>
    suspend fun retrieveAccessToken(): Result<String>
    suspend fun logout(): Result<String>

}
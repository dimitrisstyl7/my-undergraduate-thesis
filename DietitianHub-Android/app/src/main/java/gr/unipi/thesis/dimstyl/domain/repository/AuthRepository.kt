package gr.unipi.thesis.dimstyl.domain.repository

import gr.unipi.thesis.dimstyl.data.model.LoginRequest

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest): Result<String>

}
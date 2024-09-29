package gr.unipi.thesis.dimstyl.data.sources.remote

import gr.unipi.thesis.dimstyl.data.models.LoginRequest
import gr.unipi.thesis.dimstyl.data.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/authenticate")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

}
package gr.unipi.thesis.dimstyl.data.source.remote

import gr.unipi.thesis.dimstyl.data.model.LoginRequest
import gr.unipi.thesis.dimstyl.data.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/authenticate")
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

}
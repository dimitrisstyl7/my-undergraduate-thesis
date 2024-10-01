package gr.unipi.thesis.dimstyl.data.sources.remote.services

import retrofit2.Response
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AppointmentApiService {

    @PATCH("appointments/{id}/cancel")
    suspend fun cancelAppointment(@Path("id") id: Int): Response<Unit>

}
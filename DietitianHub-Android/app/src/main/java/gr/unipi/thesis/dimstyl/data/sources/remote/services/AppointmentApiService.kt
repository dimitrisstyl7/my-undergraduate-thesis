package gr.unipi.thesis.dimstyl.data.sources.remote.services

import gr.unipi.thesis.dimstyl.data.models.Appointment
import gr.unipi.thesis.dimstyl.data.models.AppointmentsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AppointmentApiService {

    @GET("appointments")
    suspend fun fetchAppointments(): Response<AppointmentsResponse>

    @POST("appointments")
    suspend fun createAppointment(@Body requestedDatetime: Appointment): Response<Appointment>

    @PATCH("appointments/{id}/cancel")
    suspend fun cancelAppointment(@Path("id") id: Int): Response<Unit>

}
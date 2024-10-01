package gr.unipi.thesis.dimstyl.data.repositories

import android.util.Log
import gr.unipi.thesis.dimstyl.data.models.Appointment
import gr.unipi.thesis.dimstyl.data.models.AppointmentsResponse
import gr.unipi.thesis.dimstyl.data.sources.remote.services.AppointmentApiService
import gr.unipi.thesis.dimstyl.data.utils.ErrorParser.extractErrorCode
import gr.unipi.thesis.dimstyl.domain.repositories.AppointmentRepository
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.APPOINTMENT_ALREADY_EXISTS_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.CANCEL_APPOINTMENT_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.CREATE_APPOINTMENT_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.FETCH_APPOINTMENTS_ERROR_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AppointmentRepositoryImpl(private val appointmentApiService: AppointmentApiService) :
    AppointmentRepository {

    companion object {
        private const val TAG = "AppointmentRepositoryImpl"
    }

    override suspend fun fetchAppointments(): Result<AppointmentsResponse> {
        return withContext(Dispatchers.IO) {
            val response: Response<AppointmentsResponse>

            try {
                response = appointmentApiService.fetchAppointments()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch appointments", e)
                return@withContext Result.failure(Exception(FETCH_APPOINTMENTS_ERROR_MESSAGE))
            }

            if (response.isSuccessful) {
                val body: AppointmentsResponse? = response.body()

                when {
                    body == null -> {
                        Log.e(TAG, "Failed to fetch appointments: Response body is null")
                        return@withContext Result.failure(Exception(FETCH_APPOINTMENTS_ERROR_MESSAGE))
                    }

                    else -> return@withContext Result.success(body)
                }
            } else {
                Log.e(TAG, "Failed to fetch appointments: ${response.errorBody()?.string()}")
                return@withContext Result.failure(Exception(FETCH_APPOINTMENTS_ERROR_MESSAGE))
            }
        }
    }

    override suspend fun createAppointment(appointment: Appointment): Result<Appointment> {
        return withContext(Dispatchers.IO) {
            val response: Response<Appointment>

            try {
                response = appointmentApiService.createAppointment(appointment)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to create appointment", e)
                return@withContext Result.failure(Exception(CREATE_APPOINTMENT_ERROR_MESSAGE))
            }

            if (response.isSuccessful) {
                val body: Appointment? = response.body()

                when {
                    body == null -> {
                        Log.e(TAG, "Failed to create appointment: Response body is null")
                        return@withContext Result.failure(Exception(CREATE_APPOINTMENT_ERROR_MESSAGE))
                    }

                    else -> return@withContext Result.success(body)
                }
            } else {
                val errorResponse = response.errorBody()?.string()
                val errorCode = extractErrorCode(errorResponse)
                var errorMessage = CREATE_APPOINTMENT_ERROR_MESSAGE

                Log.e(TAG, "Failed to create appointment: $errorResponse")

                if (errorCode != null && errorCode == 409) {
                    errorMessage = APPOINTMENT_ALREADY_EXISTS_ERROR_MESSAGE
                }

                return@withContext Result.failure(Exception(errorMessage))
            }
        }
    }

    override suspend fun cancelAppointment(id: Int): Result<Unit> {
        return withContext(Dispatchers.IO) {
            val response: Response<Unit>

            try {
                response = appointmentApiService.cancelAppointment(id)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to cancel appointment", e)
                return@withContext Result.failure(Exception(CANCEL_APPOINTMENT_ERROR_MESSAGE))
            }

            if (response.isSuccessful) return@withContext Result.success(Unit)
            else {
                Log.e(TAG, "Failed to cancel appointment: ${response.errorBody()?.string()}")
                return@withContext Result.failure(Exception(CANCEL_APPOINTMENT_ERROR_MESSAGE))
            }
        }
    }

}
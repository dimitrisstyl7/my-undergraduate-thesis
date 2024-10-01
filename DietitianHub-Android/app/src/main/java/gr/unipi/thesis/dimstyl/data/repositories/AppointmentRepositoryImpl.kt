package gr.unipi.thesis.dimstyl.data.repositories

import android.util.Log
import gr.unipi.thesis.dimstyl.data.sources.remote.services.AppointmentApiService
import gr.unipi.thesis.dimstyl.data.utils.ErrorParser.handleErrorResponse
import gr.unipi.thesis.dimstyl.domain.repositories.AppointmentRepository
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.DEFAULT_CANCEL_APPOINTMENT_ERROR_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AppointmentRepositoryImpl(private val appointmentApiService: AppointmentApiService) :
    AppointmentRepository {

    companion object {
        private const val TAG = "AppointmentRepositoryImpl"
    }

    override suspend fun cancelAppointment(id: Int): Result<Unit> {
        return withContext(Dispatchers.IO) {
            val response: Response<Unit>

            try {
                response = appointmentApiService.cancelAppointment(id)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to cancel appointment", e)
                return@withContext Result.failure(Exception(DEFAULT_CANCEL_APPOINTMENT_ERROR_MESSAGE))
            }

            if (response.isSuccessful) return@withContext Result.success(Unit)
            else {
                val errorBody = response.errorBody()?.string()
                val errorMessage =
                    handleErrorResponse(errorBody, DEFAULT_CANCEL_APPOINTMENT_ERROR_MESSAGE)
                Log.e(TAG, "Failed to cancel appointment: $errorMessage")
                return@withContext Result.failure(Exception(errorMessage))
            }
        }
    }
}
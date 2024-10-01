package gr.unipi.thesis.dimstyl.data.utils

import android.util.Log
import com.google.gson.Gson
import gr.unipi.thesis.dimstyl.data.models.ErrorResponse

private const val TAG = "ErrorHandler"

object ErrorParser {

    fun handleErrorResponse(errorBody: String?, defaultErrorMessage: String): String {
        return errorBody?.let { parseResponseErrorBody(it, defaultErrorMessage) }
            ?: defaultErrorMessage
    }

    private fun parseResponseErrorBody(errorBody: String, defaultErrorMessage: String): String {
        return try {
            Gson().fromJson(errorBody, ErrorResponse::class.java)?.message ?: defaultErrorMessage
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse error response", e)
            defaultErrorMessage
        }
    }

}
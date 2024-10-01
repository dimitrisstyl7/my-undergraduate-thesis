package gr.unipi.thesis.dimstyl.data.utils

import android.util.Log
import com.google.gson.Gson
import gr.unipi.thesis.dimstyl.data.models.ErrorResponse

private const val TAG = "ErrorHandler"

object ErrorParser {

    fun extractErrorMessage(errorBody: String?, defaultErrorMessage: String): String {
        return errorBody?.let { parseErrorBody(it)?.message ?: defaultErrorMessage }
            ?: defaultErrorMessage
    }

    fun extractErrorCode(errorBody: String?): Int? {
        return errorBody?.let { parseErrorBody(it)?.statusCode }
    }

    private fun parseErrorBody(errorBody: String): ErrorResponse? {
        return try {
            Gson().fromJson(errorBody, ErrorResponse::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse error response", e)
            null
        }
    }

}
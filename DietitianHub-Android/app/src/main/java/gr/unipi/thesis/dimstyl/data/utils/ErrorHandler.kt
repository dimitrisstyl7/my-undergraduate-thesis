package gr.unipi.thesis.dimstyl.data.utils

import android.util.Log
import com.google.gson.Gson
import gr.unipi.thesis.dimstyl.data.models.ErrorResponse

private const val TAG = "ErrorHandler"

object ErrorMessages {

    const val DEFAULT_LOGIN_ERROR_MESSAGE =
        """Something went wrong while logging you in.
Please check your credentials and try again.
If the problem persists, please contact our Support team for assistance."""

}

object ErrorParser {

    fun parseResponseErrorBody(errorBody: String, defaultErrorMessage: String): String {
        return runCatching {
            Gson().fromJson(errorBody, ErrorResponse::class.java)?.message ?: defaultErrorMessage
        }.getOrElse {
            Log.e(TAG, "Failed to parse error response: ${it.message}")
            defaultErrorMessage
        }
    }

}
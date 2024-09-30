package gr.unipi.thesis.dimstyl.utils

class Constants {

    object ErrorMessages {

        const val DEFAULT_LOGIN_ERROR_MESSAGE =
            """Something went wrong while logging you in.
Please check your credentials and try again.
If the problem persists, contact our support team for assistance."""

        const val TOKEN_RETRIEVAL_FAILED_ERROR_MESSAGE =
            """Something went wrong while trying to retrieve your session. Please log in again to continue."""

        const val TOKEN_EXPIRED_ERROR_MESSAGE =
            """Your session has expired. Please log in again to continue."""

    }

    object SuccessMessages {

        const val MANUAL_LOGIN_SUCCESS_MESSAGE = "You have successfully logged in!"
        const val AUTO_LOGIN_SUCCESS_MESSAGE = "Welcome back!"

    }

}
package gr.unipi.thesis.dimstyl.utils

class Constants {

    object ErrorMessages {

        const val DEFAULT_LOGIN_ERROR_MESSAGE =
            "Something went wrong while logging you in.\n" +
                    "Please check your credentials and try again.\n" +
                    "If the problem persists, contact our support team for assistance."

        const val TOKEN_RETRIEVAL_FAILED_ERROR_MESSAGE =
            "Something went wrong while trying to retrieve your session. Please log in again to continue."

        const val TOKEN_EXPIRED_ERROR_MESSAGE =
            "Your session has expired. Please log in again to continue."

        const val FETCH_DATA_ERROR_MESSAGE =
            "An error occurred while fetching data. Please try again later. " +
                    "If the problem persists, contact our support team for assistance."

        const val DEFAULT_CANCEL_APPOINTMENT_ERROR_MESSAGE =
            "An error occurred while trying to cancel your appointment. Please try again later. " +
                    "If the problem persists, contact our support team for assistance."

    }

    object SuccessMessages {

        const val MANUAL_LOGIN_SUCCESS_MESSAGE = "You have successfully logged in!"
        const val AUTO_LOGIN_SUCCESS_MESSAGE = "Welcome back!"
        const val APPOINTMENT_CANCELED_SUCCESS_MESSAGE =
            "Your appointment has been canceled successfully!"

    }

}
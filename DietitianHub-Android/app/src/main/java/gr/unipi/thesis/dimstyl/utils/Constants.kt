package gr.unipi.thesis.dimstyl.utils

class Constants {

    object BaseUrl {

        const val LOCALHOST = "http://10.0.2.2:8080/api/v1/"

    }

    object Authorization {

        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"

    }

    object ErrorMessages {

        const val LOGIN_ERROR_MESSAGE =
            "Something went wrong while trying to log you in.\n" +
                    "Please check your credentials and try again.\n" +
                    "If the problem persists, contact our support team for assistance."

        const val LOGOUT_ERROR_MESSAGE =
            "An error occurred while trying to log you out. Please try again later. " +
                    "If the problem persists, contact our support team for assistance."

        const val TOKEN_RETRIEVAL_FAILED_ERROR_MESSAGE =
            "Something went wrong while trying to retrieve your session. Please log in again to continue."

        const val TOKEN_EXPIRED_ERROR_MESSAGE =
            "Your session has expired. Please log in again to continue."

        const val FETCH_DATA_ERROR_MESSAGE =
            "An error occurred while fetching data. Please try again later. " +
                    "If the problem persists, contact our support team for assistance."

        const val CANCEL_APPOINTMENT_ERROR_MESSAGE =
            "An error occurred while trying to cancel your appointment. Please try again later. " +
                    "If the problem persists, contact our support team for assistance."

        const val FETCH_APPOINTMENTS_ERROR_MESSAGE =
            "An error occurred while trying to fetch your appointments. Please try again later. " +
                    "If the problem persists, contact our support team for assistance."

        const val CREATE_APPOINTMENT_ERROR_MESSAGE =
            "An error occurred while trying to request a new appointment. Please try again later. " +
                    "If the problem persists, contact our support team for assistance."

        const val APPOINTMENT_ALREADY_EXISTS_ERROR_MESSAGE =
            "You already have an appointment for this date and time. " +
                    "Please cancel your existing appointment before requesting a new one."

        const val FETCH_ARTICLES_ERROR_MESSAGE =
            "An error occurred while trying to fetch articles. Please try again later. " +
                    "If the problem persists, contact our support team for assistance."

        const val FETCH_DIET_PLANS_ERROR_MESSAGE =
            "An error occurred while trying to fetch diet plans. Please try again later. " +
                    "If the problem persists, contact our support team for assistance."

        const val UNKNOWN_ERROR_MESSAGE =
            "An unknown error occurred. Please try again later. " +
                    "If the problem persists, contact our support team for assistance."

    }

    object SuccessMessages {

        const val MANUAL_LOGIN_SUCCESS_MESSAGE = "You have successfully logged in!"

        const val AUTO_LOGIN_SUCCESS_MESSAGE = "Welcome back!"

        const val LOGOUT_SUCCESS_MESSAGE = "You have successfully logged out!"

        const val APPOINTMENT_CANCELLED_SUCCESS_MESSAGE =
            "Your appointment has been cancelled successfully!"

        const val APPOINTMENT_CREATED_SUCCESS_MESSAGE =
            "Your appointment has been requested successfully!"

    }

}
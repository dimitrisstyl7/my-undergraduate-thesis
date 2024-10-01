package gr.unipi.thesis.dimstyl.presentation.screens.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.unipi.thesis.dimstyl.domain.usecases.CheckTokenValidityUseCase
import gr.unipi.thesis.dimstyl.exceptions.JwtAccessTokenDoesNotExist
import gr.unipi.thesis.dimstyl.exceptions.JwtAccessTokenExpired
import gr.unipi.thesis.dimstyl.exceptions.JwtAccessTokenRetrievalFailed
import gr.unipi.thesis.dimstyl.exceptions.UnknownException
import gr.unipi.thesis.dimstyl.presentation.utils.LoginStatus
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.TOKEN_EXPIRED_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.TOKEN_RETRIEVAL_FAILED_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.SuccessMessages.AUTO_LOGIN_SUCCESS_MESSAGE
import kotlinx.coroutines.launch

class LandingViewModel(
    private val checkTokenValidityUseCase: CheckTokenValidityUseCase
) : ViewModel() {

    fun resolveLoginStatus(onLoginStatusResolved: (LoginStatus, Boolean, String, Boolean) -> Unit) {
        viewModelScope.launch {
            val result = checkTokenValidityUseCase.execute()
            var status = LoginStatus.LOGGED_OUT
            var showSnackbarMessage = false
            var snackbarMessage = ""


            if (result.isSuccess) {
                showSnackbarMessage = true
                status = LoginStatus.LOGGED_IN
                snackbarMessage = AUTO_LOGIN_SUCCESS_MESSAGE
            } else {
                val exception = result.exceptionOrNull()

                when (exception) {
                    is JwtAccessTokenExpired -> {
                        showSnackbarMessage = true
                        snackbarMessage = TOKEN_EXPIRED_ERROR_MESSAGE
                    }

                    is JwtAccessTokenRetrievalFailed -> {
                        showSnackbarMessage = true
                        snackbarMessage = TOKEN_RETRIEVAL_FAILED_ERROR_MESSAGE
                    }

                    is JwtAccessTokenDoesNotExist, is UnknownException -> {
                        showSnackbarMessage = false
                        snackbarMessage = ""
                    }
                }
            }

            onLoginStatusResolved(status, showSnackbarMessage, snackbarMessage, true)
        }
    }

}
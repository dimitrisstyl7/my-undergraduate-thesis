package gr.unipi.thesis.dimstyl.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.unipi.thesis.dimstyl.domain.usecases.LoginUseCase
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.DEFAULT_LOGIN_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.SuccessMessages.MANUAL_LOGIN_SUCCESS_MESSAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun login(onSuccessfulLogin: (String, String) -> Unit) {
        viewModelScope.launch {
            val result = loginUseCase.execute(state.value.username, state.value.password)
            val token = result.getOrNull()
            val errorMessage = result.exceptionOrNull()?.message ?: DEFAULT_LOGIN_ERROR_MESSAGE

            _state.value = _state.value.copy(
                loginHasError = result.isFailure,
                errorMessage = errorMessage,
                processingLoginRequest = false
            )

            if (result.isSuccess && !token.isNullOrBlank()) {
                onSuccessfulLogin(MANUAL_LOGIN_SUCCESS_MESSAGE, token)
            }
        }
    }

    fun setUsername(username: String) {
        _state.value = _state.value.copy(username = username)
    }

    fun setPassword(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun togglePasswordVisibility() {
        _state.value = _state.value.copy(isPasswordVisible = !_state.value.isPasswordVisible)
    }

    fun setProcessingLoginRequest(processing: Boolean) {
        _state.value = _state.value.copy(processingLoginRequest = processing)
    }

}
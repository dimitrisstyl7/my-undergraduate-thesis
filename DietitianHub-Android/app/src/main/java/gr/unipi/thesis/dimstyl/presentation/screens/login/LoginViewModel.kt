package gr.unipi.thesis.dimstyl.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.unipi.thesis.dimstyl.domain.usecases.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun login(onSuccessfulLogin: (String) -> Unit) {
        viewModelScope.launch {
            val result = loginUseCase.execute(state.value.username, state.value.password)

            if (result.isSuccess) onSuccessfulLogin(result.getOrNull()!!)
            else {
                _state.value = _state.value.copy(loginHasError = true)
                _state.value = _state.value.copy(
                    errorMessage = result.exceptionOrNull()?.message!!
                )
            }

            _state.value = _state.value.copy(processingLoginRequest = false)
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
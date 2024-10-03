package gr.unipi.thesis.dimstyl.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.unipi.thesis.dimstyl.domain.usecases.LogoutUseCase
import gr.unipi.thesis.dimstyl.presentation.navigation.NavRoute
import gr.unipi.thesis.dimstyl.presentation.utils.LoginStatus
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.LOGOUT_ERROR_MESSAGE
import gr.unipi.thesis.dimstyl.utils.Constants.SuccessMessages.LOGOUT_SUCCESS_MESSAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val logoutUseCase: LogoutUseCase) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    fun logout(onLogoutResult: (String, Boolean) -> Unit, onLogoutSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = logoutUseCase()

            if (result.isSuccess) {
                val successMessage = result.getOrNull() ?: LOGOUT_SUCCESS_MESSAGE
                setLoginStatus(LoginStatus.LOGGED_OUT)
                onLogoutResult(successMessage, true)
                onLogoutSuccess()
            } else {
                val errorMessage = result.exceptionOrNull()?.message ?: LOGOUT_ERROR_MESSAGE
                onLogoutResult(errorMessage, false)
            }
        }
    }

    fun showLogoutDialog(show: Boolean) {
        _state.value = _state.value.copy(showLogoutDialog = show)
    }

    fun setTopBarTitle(title: String) {
        _state.value = _state.value.copy(topBarTitle = title)
    }

    fun setCurrentNavRoute(route: NavRoute) {
        _state.value = _state.value.copy(currentNavRoute = route)
    }

    fun setLoginStatus(status: LoginStatus) {
        _state.value = _state.value.copy(loginStatus = status)
    }

    fun setJwtToken(token: String) {
        _state.value = _state.value.copy(jwtToken = token)
    }

}
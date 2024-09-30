package gr.unipi.thesis.dimstyl.presentation.screens.main

import androidx.lifecycle.ViewModel
import gr.unipi.thesis.dimstyl.presentation.navigation.NavRoute
import gr.unipi.thesis.dimstyl.presentation.utils.LoginStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        // TODO: Check if user is already logged in
        _state.value = _state.value.copy(loginStatus = LoginStatus.LOGGED_OUT)
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

    fun setJwtAccessToken(token: String) {
        _state.value = _state.value.copy(jwtAccessToken = token)
    }

}
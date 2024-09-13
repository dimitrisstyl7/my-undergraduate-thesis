package gr.unipi.thesis.dimstyl.ui.screens.main

import androidx.lifecycle.ViewModel
import gr.unipi.thesis.dimstyl.ui.navigation.NavRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    fun openLogoutDialog() {
        _state.value = _state.value.copy(openLogoutDialog = true)
    }

    fun closeLogoutDialog() {
        _state.value = _state.value.copy(openLogoutDialog = false)
    }

    fun setTopBarTitle(title: String) {
        _state.value = _state.value.copy(topBarTitle = title)
    }

    fun setCurrentNavRoute(route: NavRoute) {
        _state.value = _state.value.copy(currentNavRoute = route)
    }

}
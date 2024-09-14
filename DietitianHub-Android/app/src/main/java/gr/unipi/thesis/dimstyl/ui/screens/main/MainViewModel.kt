package gr.unipi.thesis.dimstyl.ui.screens.main

import androidx.lifecycle.ViewModel
import gr.unipi.thesis.dimstyl.ui.navigation.NavRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    fun showLogoutDialog(show: Boolean) {
        _state.value = _state.value.copy(showLogoutDialog = show)
    }

    fun setTopBarTitle(title: String) {
        _state.value = _state.value.copy(topBarTitle = title)
    }

    fun setCurrentNavRoute(route: NavRoute) {
        _state.value = _state.value.copy(currentNavRoute = route)
    }

}
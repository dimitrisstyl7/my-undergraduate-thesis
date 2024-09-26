package gr.unipi.thesis.dimstyl.ui.screens.main

import gr.unipi.thesis.dimstyl.ui.helpers.LoginStatus
import gr.unipi.thesis.dimstyl.ui.navigation.NavRoute

data class MainState(
    val showLogoutDialog: Boolean = false,
    val topBarTitle: String = NavRoute.HOME.toString(),
    val currentNavRoute: NavRoute = NavRoute.LANDING,
    val loginStatus: LoginStatus = LoginStatus.UNKNOWN
)
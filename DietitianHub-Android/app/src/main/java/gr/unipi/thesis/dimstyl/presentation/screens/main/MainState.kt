package gr.unipi.thesis.dimstyl.presentation.screens.main

import gr.unipi.thesis.dimstyl.presentation.navigation.NavRoute
import gr.unipi.thesis.dimstyl.presentation.utils.LoginStatus

data class MainState(
    val showLogoutDialog: Boolean = false,
    val topBarTitle: String = NavRoute.HOME.toString(),
    val currentNavRoute: NavRoute = NavRoute.LANDING,
    val loginStatus: LoginStatus = LoginStatus.UNKNOWN,
    val jwtAccessToken: String = ""
)
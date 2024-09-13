package gr.unipi.thesis.dimstyl.ui.screens.main

import gr.unipi.thesis.dimstyl.ui.navigation.NavRoute

data class MainState(
    val openLogoutDialog: Boolean = false,
    val topBarTitle: String = "Home",
    val currentNavRoute: NavRoute = NavRoute.HOME
)
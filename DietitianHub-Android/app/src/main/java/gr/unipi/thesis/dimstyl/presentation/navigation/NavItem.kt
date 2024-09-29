package gr.unipi.thesis.dimstyl.presentation.navigation

import androidx.annotation.DrawableRes

class NavItem(
    @DrawableRes val iconRes: Int,
    val route: NavRoute,
    val navLabel: String = route.toString()
)
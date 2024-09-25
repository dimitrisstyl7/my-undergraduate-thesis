package gr.unipi.thesis.dimstyl.ui.navigation

import androidx.annotation.DrawableRes

class NavItem(
    @DrawableRes val iconRes: Int,
    val route: NavRoute,
    val navLabel: String = route.toString()
)
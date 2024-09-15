package gr.unipi.thesis.dimstyl.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import gr.unipi.thesis.dimstyl.R

val ProfileNavItem = NavItem(
    icon = Icons.Rounded.AccountCircle,
    topBarTitle = "Profile",
    navLabel = "Profile",
    iconDescription = "Profile",
    route = NavRoute.PROFILE
)

val DietPlansNavItem = NavItem(
    iconRes = R.drawable.rounded_menstrual_health_24,
    topBarTitle = "Diet Plans",
    navLabel = "Diet Plans",
    iconDescription = "Diet Plans",
    route = NavRoute.DIET_PLANS
)
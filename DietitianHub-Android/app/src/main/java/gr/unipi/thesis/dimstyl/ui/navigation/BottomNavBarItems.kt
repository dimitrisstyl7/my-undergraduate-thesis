package gr.unipi.thesis.dimstyl.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import gr.unipi.thesis.dimstyl.R

val HomeNavItem = NavItem(
    icon = Icons.Rounded.Home,
    topBarTitle = "Home",
    navLabel = "Home",
    iconDescription = "Home",
    route = NavRoute.HOME
)

val NewAppointmentNavItem = NavItem(
    iconRes = R.drawable.rounded_calendar_add_on_24,
    topBarTitle = "New Appointment",
    navLabel = "Appointment",
    iconDescription = "New Appointment",
    route = NavRoute.NEW_APPOINTMENT
)

val bottomNavBarItems = listOf(
    HomeNavItem, NewAppointmentNavItem, ProfileNavItem
)
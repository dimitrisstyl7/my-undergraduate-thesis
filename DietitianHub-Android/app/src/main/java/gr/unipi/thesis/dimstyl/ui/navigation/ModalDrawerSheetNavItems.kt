package gr.unipi.thesis.dimstyl.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import gr.unipi.thesis.dimstyl.R

val AppointmentsNavItem = NavItem(
    iconRes = R.drawable.rounded_calendar_month_24,
    topBarTitle = "Appointments",
    navLabel = "Appointments",
    iconDescription = "Appointments",
    route = NavRoute.APPOINTMENTS
)

val AnnouncementsNavItem = NavItem(
    iconRes = R.drawable.announcement_svgrepo_com,
    topBarTitle = "Announcements",
    navLabel = "Announcements",
    iconDescription = "Announcements",
    route = NavRoute.ANNOUNCEMENTS
)

val ArticlesNavItem = NavItem(
    iconRes = R.drawable.rounded_article_24,
    topBarTitle = "Articles",
    navLabel = "Articles",
    iconDescription = "Articles",
    route = NavRoute.ARTICLES
)

val LogoutNavItem = NavItem(
    icon = Icons.AutoMirrored.Default.ExitToApp,
    navLabel = "Logout",
    iconDescription = "Logout",
    route = NavRoute.LOGOUT
)

val modalDrawerSheetItems = listOf(
    ProfileNavItem,
    DietPlansNavItem,
    AppointmentsNavItem,
    AnnouncementsNavItem,
    ArticlesNavItem,
    LogoutNavItem
)
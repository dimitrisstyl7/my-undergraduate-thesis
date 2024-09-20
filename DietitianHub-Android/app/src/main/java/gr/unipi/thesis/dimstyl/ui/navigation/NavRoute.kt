package gr.unipi.thesis.dimstyl.ui.navigation

import kotlinx.serialization.Serializable

enum class NavRoute {

    HOME,
    PROFILE,
    APPOINTMENTS,
    ARTICLES,
    ANNOUNCEMENTS,
    DIET_PLANS,
    LOGOUT;

    fun getRoute(): Any = when (this) {
        HOME -> Home
        PROFILE -> Profile
        APPOINTMENTS -> Appointments
        ARTICLES -> Articles
        ANNOUNCEMENTS -> Announcements
        DIET_PLANS -> DietPlans
        LOGOUT -> Unit /* TODO: Navigate to login screen */
    }

    override fun toString(): String =
        when (this) {
            HOME -> "Home"
            PROFILE -> "Profile"
            APPOINTMENTS -> "Appointments"
            ARTICLES -> "Articles"
            ANNOUNCEMENTS -> "Announcements"
            DIET_PLANS -> "Diet Plans"
            LOGOUT -> "Logout"
        }
}

@Serializable
object Home

@Serializable
object Profile

@Serializable
object Appointments

@Serializable
object Articles

@Serializable
object Announcements

@Serializable
object DietPlans

val navRoutes = NavRoute.entries.toList()
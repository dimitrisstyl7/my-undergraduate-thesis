package gr.unipi.thesis.dimstyl.presentation.navigation

import kotlinx.serialization.Serializable

enum class NavRoute {

    LANDING,
    HOME,
    PROFILE,
    APPOINTMENTS,
    ARTICLES,
    ANNOUNCEMENTS,
    DIET_PLANS,
    LOGIN,
    LOGOUT;

    fun getRoute(): Any = when (this) {
        LANDING -> Landing
        HOME -> Home
        PROFILE -> Profile
        APPOINTMENTS -> Appointments
        ARTICLES -> Articles
        ANNOUNCEMENTS -> Announcements
        DIET_PLANS -> DietPlans
        LOGIN, LOGOUT -> Login
    }

    override fun toString(): String =
        when (this) {
            LANDING -> "Landing"
            HOME -> "Home"
            PROFILE -> "Profile"
            APPOINTMENTS -> "Appointments"
            ARTICLES -> "Articles"
            ANNOUNCEMENTS -> "Announcements"
            DIET_PLANS -> "Diet Plans"
            LOGIN -> "Log In"
            LOGOUT -> "Log Out"
        }
}

@Serializable
object Landing

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

@Serializable
object Login

val navRoutes = NavRoute.entries.toList()
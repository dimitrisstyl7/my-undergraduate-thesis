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

    companion object {
        fun getRoute(route: NavRoute, id: Int? = null): Any {
            return when (route) {
                HOME -> Home
                PROFILE -> Profile
                APPOINTMENTS -> Appointments
                ARTICLES -> Articles
                ANNOUNCEMENTS -> Announcements
                DIET_PLANS -> DietPlans
                LOGOUT -> Unit /* TODO: Navigate to login screen */
            }
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

}

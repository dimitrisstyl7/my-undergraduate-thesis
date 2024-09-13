package gr.unipi.thesis.dimstyl.ui.navigation

import kotlinx.serialization.Serializable

enum class NavRoute {

    HOME,
    PROFILE,
    APPOINTMENTS, NEW_APPOINTMENT,
    ARTICLES, ARTICLE_DETAILS,
    ANNOUNCEMENTS, ANNOUNCEMENT_DETAILS,
    DIET_PLANS,
    LOGOUT;

    companion object {
        fun getRoute(route: NavRoute, id: Int? = null): Any {
            return when (route) {
                HOME -> Home
                PROFILE -> Profile
                APPOINTMENTS -> Appointments()
                NEW_APPOINTMENT -> Appointments(true)
                ARTICLES, ARTICLE_DETAILS -> Articles(id)
                ANNOUNCEMENTS, ANNOUNCEMENT_DETAILS -> Announcements(id)
                DIET_PLANS -> DietPlans(id)
                LOGOUT -> Unit /* TODO: Navigate to login screen */
            }
        }
    }

    @Serializable
    object Home

    @Serializable
    object Profile

    @Serializable
    data class Appointments(val create: Boolean = false)

    @Serializable
    data class Articles(val id: Int?)

    @Serializable
    data class Announcements(val id: Int?)

    @Serializable
    data class DietPlans(val id: Int?)

}

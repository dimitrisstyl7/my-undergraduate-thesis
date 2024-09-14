package gr.unipi.thesis.dimstyl.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import gr.unipi.thesis.dimstyl.ui.screens.announcements.AnnouncementDetailScreen
import gr.unipi.thesis.dimstyl.ui.screens.announcements.AnnouncementsScreen
import gr.unipi.thesis.dimstyl.ui.screens.appointments.AppointmentsScreen
import gr.unipi.thesis.dimstyl.ui.screens.appointments.NewAppointmentScreen
import gr.unipi.thesis.dimstyl.ui.screens.articles.ArticleDetailScreen
import gr.unipi.thesis.dimstyl.ui.screens.articles.ArticlesScreen
import gr.unipi.thesis.dimstyl.ui.screens.dietPlans.DietPlansScreen
import gr.unipi.thesis.dimstyl.ui.screens.home.HomeScreen
import gr.unipi.thesis.dimstyl.ui.screens.profile.ProfileScreen

@Composable
fun AppNavHost(navController: NavController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = NavRoute.Home,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable<NavRoute.Home> { HomeScreen() }

        composable<NavRoute.Profile> { ProfileScreen() }

        composable<NavRoute.Appointments> { backStackEntry ->
            val create = (backStackEntry.toRoute() as NavRoute.Appointments).create
            if (create) NewAppointmentScreen() else AppointmentsScreen()
        }

        composable<NavRoute.Articles> { backStackEntry ->
            val id = (backStackEntry.toRoute() as NavRoute.Articles).id
            if (id != null) ArticleDetailScreen(id) else ArticlesScreen()
        }

        composable<NavRoute.Announcements> { backStackEntry ->
            val id = (backStackEntry.toRoute() as NavRoute.Announcements).id
            if (id != null) AnnouncementDetailScreen(id) else AnnouncementsScreen()
        }

        composable<NavRoute.DietPlans> { DietPlansScreen() }
    }
}
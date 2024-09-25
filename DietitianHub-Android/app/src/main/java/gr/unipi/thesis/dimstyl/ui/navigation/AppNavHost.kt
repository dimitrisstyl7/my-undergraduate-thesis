package gr.unipi.thesis.dimstyl.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import gr.unipi.thesis.dimstyl.ui.screens.announcements.AnnouncementsScreen
import gr.unipi.thesis.dimstyl.ui.screens.appointments.AppointmentsScreen
import gr.unipi.thesis.dimstyl.ui.screens.articles.ArticlesScreen
import gr.unipi.thesis.dimstyl.ui.screens.auth.LoginScreen
import gr.unipi.thesis.dimstyl.ui.screens.dietPlans.DietPlansScreen
import gr.unipi.thesis.dimstyl.ui.screens.home.HomeScreen
import gr.unipi.thesis.dimstyl.ui.screens.profile.ProfileScreen

@Composable
fun AppNavHost(
    navController: NavController,
    backHandler: @Composable () -> Unit,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Home,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable<Home> { HomeScreen() }

        composable<Profile> { ProfileScreen() }

        composable<Appointments> { AppointmentsScreen() }

        composable<Articles> { ArticlesScreen() }

        composable<Announcements> { AnnouncementsScreen() }

        composable<DietPlans> { DietPlansScreen() }
    }

    backHandler()
}
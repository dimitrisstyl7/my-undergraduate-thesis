package gr.unipi.thesis.dimstyl.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import gr.unipi.thesis.dimstyl.ui.helpers.LoginStatus
import gr.unipi.thesis.dimstyl.ui.screens.announcements.AnnouncementsScreen
import gr.unipi.thesis.dimstyl.ui.screens.appointments.AppointmentsScreen
import gr.unipi.thesis.dimstyl.ui.screens.articles.ArticlesScreen
import gr.unipi.thesis.dimstyl.ui.screens.dietPlans.DietPlansScreen
import gr.unipi.thesis.dimstyl.ui.screens.home.HomeScreen
import gr.unipi.thesis.dimstyl.ui.screens.landing.LandingScreen
import gr.unipi.thesis.dimstyl.ui.screens.login.LoginScreen
import gr.unipi.thesis.dimstyl.ui.screens.main.MainState
import gr.unipi.thesis.dimstyl.ui.screens.main.MainViewModel
import gr.unipi.thesis.dimstyl.ui.screens.profile.ProfileScreen

@Composable
fun AppNavHost(
    navController: NavController,
    viewModel: MainViewModel,
    mainState: MainState,
    backHandler: @Composable () -> Unit,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Landing,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable<Landing> {
            LandingScreen(
                loginStatus = mainState.loginStatus,
                onLoginStatusResolved = {
                    viewModel.setCurrentNavRoute(NavRoute.HOME)
                    navController.navigate(Home) { popUpTo(Landing) { inclusive = true } }
                }
            )
        }

        composable<Home> {
            HomeScreen(
                loginStatus = mainState.loginStatus,
                onNavigateToLoginScreen = {
                    viewModel.setLoginStatus(LoginStatus.LOGGED_OUT)
                    viewModel.setCurrentNavRoute(NavRoute.LOGIN)
                    navController.navigate(Login) { popUpTo(Home) { inclusive = true } }
                }
            )
        }

        composable<Profile> { ProfileScreen() }

        composable<Appointments> { AppointmentsScreen() }

        composable<Articles> { ArticlesScreen() }

        composable<Announcements> { AnnouncementsScreen() }

        composable<DietPlans> { DietPlansScreen() }

        composable<Login> {
            LoginScreen {
                viewModel.setLoginStatus(LoginStatus.LOGGED_IN)
                viewModel.setCurrentNavRoute(NavRoute.HOME)
                navController.navigate(Home) { popUpTo(Login) { inclusive = true } }
            }
        }
    }

    backHandler()
}
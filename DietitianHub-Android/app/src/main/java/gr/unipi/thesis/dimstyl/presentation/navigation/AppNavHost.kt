package gr.unipi.thesis.dimstyl.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import gr.unipi.thesis.dimstyl.presentation.screens.announcements.AnnouncementsScreen
import gr.unipi.thesis.dimstyl.presentation.screens.appointments.AppointmentsScreen
import gr.unipi.thesis.dimstyl.presentation.screens.articles.ArticlesScreen
import gr.unipi.thesis.dimstyl.presentation.screens.dietPlans.DietPlansScreen
import gr.unipi.thesis.dimstyl.presentation.screens.home.HomeScreen
import gr.unipi.thesis.dimstyl.presentation.screens.landing.LandingScreen
import gr.unipi.thesis.dimstyl.presentation.screens.login.LoginScreen
import gr.unipi.thesis.dimstyl.presentation.screens.main.MainState
import gr.unipi.thesis.dimstyl.presentation.screens.main.MainViewModel
import gr.unipi.thesis.dimstyl.presentation.screens.profile.ProfileScreen
import gr.unipi.thesis.dimstyl.presentation.utils.LoginStatus

@Composable
fun AppNavHost(
    navController: NavController,
    viewModel: MainViewModel,
    mainState: MainState,
    onSnackbarShow: (String, Boolean) -> Unit,
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
                onLoginStatusResolved = { status, showSnackbarMessage, snackbarMessage, shortDuration ->
                    viewModel.setLoginStatus(status)
                    viewModel.setCurrentNavRoute(NavRoute.HOME)
                    if (showSnackbarMessage) onSnackbarShow(snackbarMessage, shortDuration)
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
                },
                onSnackbarShow = onSnackbarShow
            )
        }

        composable<Profile> { ProfileScreen() }

        composable<Appointments> { AppointmentsScreen(onSnackbarShow = onSnackbarShow) }

        composable<Articles> { ArticlesScreen() }

        composable<Announcements> { AnnouncementsScreen() }

        composable<DietPlans> { DietPlansScreen() }

        composable<Login> {
            LoginScreen(onSuccessfulLogin = { snackbarMessage, shortDuration ->
                viewModel.setLoginStatus(LoginStatus.LOGGED_IN)
                viewModel.setCurrentNavRoute(NavRoute.HOME)
                onSnackbarShow(snackbarMessage, shortDuration)
                navController.navigate(Home) { popUpTo(Login) { inclusive = true } }
            })
        }
    }

    backHandler()
}
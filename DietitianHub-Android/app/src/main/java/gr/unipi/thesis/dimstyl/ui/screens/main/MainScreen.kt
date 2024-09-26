package gr.unipi.thesis.dimstyl.ui.screens.main

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import gr.unipi.thesis.dimstyl.ui.components.BottomBar
import gr.unipi.thesis.dimstyl.ui.components.TopBar
import gr.unipi.thesis.dimstyl.ui.components.dialogs.AlertDialog
import gr.unipi.thesis.dimstyl.ui.components.modalDrawerSheet.ModalDrawerSheet
import gr.unipi.thesis.dimstyl.ui.helpers.LoginStatus
import gr.unipi.thesis.dimstyl.ui.navigation.AppNavHost
import gr.unipi.thesis.dimstyl.ui.navigation.Landing
import gr.unipi.thesis.dimstyl.ui.navigation.Login
import gr.unipi.thesis.dimstyl.ui.navigation.NavRoute
import gr.unipi.thesis.dimstyl.ui.navigation.navRoutes
import gr.unipi.thesis.dimstyl.ui.theme.BodyColor
import gr.unipi.thesis.dimstyl.ui.theme.DangerColor
import kotlinx.coroutines.launch

@SuppressLint("RestrictedApi")
/*
* Suppressed to access the current backstack size from NavController.
* This is necessary to handle system back button behavior properly.
*
* Note: Accessing the backstack size is a restricted API, so use this
* carefully and ensure future updates to the navigation library do not
* expose a safer alternative for this logic.
* */
@Composable
fun MainScreen(
    navController: NavController = rememberNavController(),
    viewModel: MainViewModel,
    exitApp: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val mainState by viewModel.state.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val entry by navController.currentBackStackEntryAsState()

    val backHandler = @Composable {
        BackHandler {
            val backStackSize = navController.currentBackStack.value.size

            when {
                drawerState.isOpen -> {
                    scope.launch { drawerState.close() }
                }

                mainState.currentNavRoute == NavRoute.LOGIN ||
                        backStackSize < 3 && mainState.currentNavRoute == NavRoute.HOME -> {
                    /*
                    * 1. If user is on the login screen, OR
                    * 2. The back stack contains only two entries (the home screen
                    *    and the start destination of the navigation graph), and
                    *    the current route is the home screen (HOME)
                    * => Exit the app
                    * */
                    exitApp()
                }

                navController.navigateUp() -> {
                    scope.launch {
                        navRoutes.forEach {
                            if (entry?.destination?.hasRoute((it.getRoute()::class)) == true) {
                                viewModel.setCurrentNavRoute(it)
                                viewModel.setTopBarTitle(it.toString())
                            }
                        }
                    }
                }

                else -> exitApp()
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = mainState.loginStatus == LoginStatus.LOGGED_IN && mainState.currentNavRoute != NavRoute.LANDING,
        drawerContent = {
            ModalDrawerSheet(
                currentNavRoute = mainState.currentNavRoute,
                onNavigate = { route, title ->
                    scope.launch {
                        drawerState.close()
                        if (route != mainState.currentNavRoute) navController.navigate(route.getRoute())
                        viewModel.setCurrentNavRoute(route)
                        viewModel.setTopBarTitle(title)
                    }
                },
                onLogout = { viewModel.showLogoutDialog(true) }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    mainState = mainState,
                    onExpandMenu = { scope.launch { drawerState.open() } }
                )
            },
            bottomBar = {
                BottomBar(
                    mainState = mainState,
                    onNavigate = { route, title ->
                        if (route != mainState.currentNavRoute) navController.navigate(route.getRoute())
                        viewModel.setCurrentNavRoute(route)
                        viewModel.setTopBarTitle(title)
                    }
                )
            },
            containerColor = BodyColor
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                viewModel = viewModel,
                mainState = mainState,
                backHandler = backHandler,
                innerPadding = innerPadding
            )

            if (mainState.showLogoutDialog) {
                AlertDialog(
                    title = "Logout",
                    text = {
                        Text(
                            text = "Are you sure you want to log out? You will need to log in again next time.",
                            textAlign = TextAlign.Center
                        )
                    },
                    confirmButtonText = "Yes, Log Out",
                    dismissButtonText = "No, Cancel",
                    icon = Icons.AutoMirrored.Rounded.ExitToApp,
                    iconContentColor = DangerColor,
                    onDismiss = { viewModel.showLogoutDialog(false) },
                    onConfirm = {
                        // TODO: Implement logout logic
                        scope.launch {
                            viewModel.showLogoutDialog(false)
                            drawerState.close()
                            viewModel.setLoginStatus(LoginStatus.LOGGED_OUT)
                            navController.navigate(Login) {
                                popUpTo(Landing) { inclusive = true }
                            }
                        }
                    }
                )
            }
        }
    }
}

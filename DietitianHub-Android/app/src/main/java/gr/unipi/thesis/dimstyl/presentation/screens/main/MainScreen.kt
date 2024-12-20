package gr.unipi.thesis.dimstyl.presentation.screens.main

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import gr.unipi.thesis.dimstyl.App
import gr.unipi.thesis.dimstyl.presentation.components.BottomBar
import gr.unipi.thesis.dimstyl.presentation.components.TopBar
import gr.unipi.thesis.dimstyl.presentation.components.WebView
import gr.unipi.thesis.dimstyl.presentation.components.dialogs.AlertDialog
import gr.unipi.thesis.dimstyl.presentation.components.modalDrawerSheet.ModalDrawerSheet
import gr.unipi.thesis.dimstyl.presentation.navigation.AppNavHost
import gr.unipi.thesis.dimstyl.presentation.navigation.Landing
import gr.unipi.thesis.dimstyl.presentation.navigation.Login
import gr.unipi.thesis.dimstyl.presentation.navigation.NavRoute
import gr.unipi.thesis.dimstyl.presentation.navigation.navRoutes
import gr.unipi.thesis.dimstyl.presentation.theme.BodyColor
import gr.unipi.thesis.dimstyl.presentation.theme.DangerColor
import gr.unipi.thesis.dimstyl.presentation.utils.LoginStatus
import gr.unipi.thesis.dimstyl.presentation.utils.viewModelFactory
import gr.unipi.thesis.dimstyl.utils.Constants.BaseUrl.WEB_LOCALHOST
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
    viewModel: MainViewModel = viewModel<MainViewModel>(
        factory = viewModelFactory { MainViewModel(App.appModule.logoutUseCase) }
    ),
    exitApp: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val mainState by viewModel.state.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }
    val entry by navController.currentBackStackEntryAsState()

    val backHandler = @Composable {
        BackHandler {
            val backStackSize = navController.currentBackStack.value.size

            when {
                mainState.showWebView -> viewModel.showWebView(false)

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

    val onSnackbarShow: (String, Boolean) -> Unit = { message, shortDuration ->
        scope.launch {
            val duration =
                if (shortDuration) SnackbarDuration.Short else SnackbarDuration.Long
            snackbarHostState.showSnackbar(message = message, duration = duration)
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
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = BodyColor
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                viewModel = viewModel,
                mainState = mainState,
                onSnackbarShow = onSnackbarShow,
                onShowWebView = { show -> viewModel.showWebView(show) },
                backHandler = backHandler,
                innerPadding = innerPadding
            )
        }
    }

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
                viewModel.showLogoutDialog(false)
                scope.launch { drawerState.close() }

                viewModel.logout(
                    onLogoutResult = { message, shortDuration ->
                        onSnackbarShow(message, shortDuration)
                    },
                    onLogoutSuccess = {
                        navController.navigate(Login) { popUpTo(Landing) { inclusive = true } }
                    }
                )
            }
        )
    }

    if (mainState.showWebView) WebView("$WEB_LOCALHOST${mainState.webViewEndpoint}", mainState.jwtToken)

}

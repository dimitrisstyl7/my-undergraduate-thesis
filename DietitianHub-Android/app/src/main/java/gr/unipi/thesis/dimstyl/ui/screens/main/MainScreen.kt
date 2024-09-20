package gr.unipi.thesis.dimstyl.ui.screens.main

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
import androidx.navigation.compose.rememberNavController
import gr.unipi.thesis.dimstyl.ui.components.BottomBar
import gr.unipi.thesis.dimstyl.ui.components.TopBar
import gr.unipi.thesis.dimstyl.ui.components.dialogs.AlertDialog
import gr.unipi.thesis.dimstyl.ui.components.modalDrawerSheet.ModalDrawerSheet
import gr.unipi.thesis.dimstyl.ui.navigation.AppNavHost
import gr.unipi.thesis.dimstyl.ui.navigation.NavRoute
import gr.unipi.thesis.dimstyl.ui.theme.BodyColor
import gr.unipi.thesis.dimstyl.ui.theme.DangerColor
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navController: NavController = rememberNavController(), viewModel: MainViewModel) {
    val scope = rememberCoroutineScope()
    val mainState by viewModel.state.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                currentNavRoute = mainState.currentNavRoute,
                onNavigate = { route, title ->
                    viewModel.setCurrentNavRoute(route)
                    viewModel.setTopBarTitle(title)
                    navController.navigate(NavRoute.getRoute(route))
                    scope.launch { drawerState.close() }
                },
                onLogout = { viewModel.showLogoutDialog(true) }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(mainState.topBarTitle) { scope.launch { drawerState.open() } }
            },
            bottomBar = {
                BottomBar(mainState.currentNavRoute) { route, title ->
                    viewModel.setCurrentNavRoute(route)
                    viewModel.setTopBarTitle(title)
                    navController.navigate(NavRoute.getRoute(route))
                }
            },
            containerColor = BodyColor
        ) { innerPadding ->
            AppNavHost(navController, innerPadding)

            if (mainState.showLogoutDialog) {
                AlertDialog(
                    title = "Logout",
                    text = {
                        Text(
                            text = "Are you sure you want to log out? You will need to log in again next time.",
                            textAlign = TextAlign.Center
                        )
                    },
                    confirmButtonText = "Yes, Logout",
                    dismissButtonText = "No, Cancel",
                    icon = Icons.AutoMirrored.Rounded.ExitToApp,
                    iconContentColor = DangerColor,
                    onDismiss = { viewModel.showLogoutDialog(false) },
                    onConfirm = {
                        // TODO: Implement logout logic
                        viewModel.showLogoutDialog(false)
                    },
                )
            }
        }
    }
}

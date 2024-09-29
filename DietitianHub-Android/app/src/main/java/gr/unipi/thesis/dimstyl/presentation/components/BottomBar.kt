package gr.unipi.thesis.dimstyl.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import gr.unipi.thesis.dimstyl.presentation.navigation.NavRoute
import gr.unipi.thesis.dimstyl.presentation.navigation.bottomNavBarItems
import gr.unipi.thesis.dimstyl.presentation.screens.main.MainState
import gr.unipi.thesis.dimstyl.presentation.theme.BottomBarColor
import gr.unipi.thesis.dimstyl.presentation.theme.TopBarColor
import gr.unipi.thesis.dimstyl.presentation.utils.LoginStatus

@Composable
fun BottomBar(
    mainState: MainState,
    onNavigate: (NavRoute, String) -> Unit
) {
    if (mainState.loginStatus != LoginStatus.LOGGED_IN || mainState.currentNavRoute == NavRoute.LANDING) return

    NavigationBar(containerColor = BottomBarColor) {
        bottomNavBarItems.forEach {
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(it.iconRes),
                        contentDescription = it.navLabel
                    )
                },
                label = { Text(it.navLabel) },
                selected = mainState.currentNavRoute == it.route,
                onClick = { onNavigate(it.route, it.navLabel) },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    selectedIconColor = TopBarColor,
                    selectedTextColor = TopBarColor,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun BottomBarPreview() {
    BottomBar(MainState(loginStatus = LoginStatus.LOGGED_IN)) { _, _ -> }
}
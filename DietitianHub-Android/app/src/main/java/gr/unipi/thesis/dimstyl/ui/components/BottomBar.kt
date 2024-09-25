package gr.unipi.thesis.dimstyl.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import gr.unipi.thesis.dimstyl.ui.navigation.NavRoute
import gr.unipi.thesis.dimstyl.ui.navigation.bottomNavBarItems
import gr.unipi.thesis.dimstyl.ui.theme.BottomBarColor
import gr.unipi.thesis.dimstyl.ui.theme.TopBarColor

@Composable
fun BottomBar(currentNavRoute: NavRoute, navigate: (NavRoute, String) -> Unit) {
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
                selected = currentNavRoute == it.route,
                onClick = { navigate(it.route, it.navLabel) },
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
    BottomBar(NavRoute.HOME) { _, _ -> }
}
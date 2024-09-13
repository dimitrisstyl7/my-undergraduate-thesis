package gr.unipi.thesis.dimstyl.ui.components.modalDrawerSheet

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import gr.unipi.thesis.dimstyl.ui.navigation.NavItem
import gr.unipi.thesis.dimstyl.ui.navigation.NavRoute
import gr.unipi.thesis.dimstyl.ui.theme.LogoutColor
import gr.unipi.thesis.dimstyl.ui.theme.TopBarColor

@Composable
fun NavigationDrawerItem(
    modifier: Modifier = Modifier,
    item: NavItem,
    selected: Boolean,
    onNavigate: (NavRoute) -> Unit
) {
    androidx.compose.material3.NavigationDrawerItem(
        modifier = modifier,
        label = {
            Text(
                text = item.navLabel,
                style = MaterialTheme.typography.titleMedium
            )
        },
        icon = {
            Icon(
                imageVector = item.getIcon(),
                contentDescription = item.iconDescription
            )
        },
        selected = selected,
        onClick = { onNavigate(item.route) },
        colors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = TopBarColor,
            selectedTextColor = TopBarColor,
            selectedContainerColor = Color.Transparent,
            unselectedIconColor = Color.White,
            unselectedTextColor = Color.White
        )
    )
}

@Composable
fun NavigationDrawerItem(
    item: NavItem,
    selected: Boolean,
    onLogout: () -> Unit
) {
    androidx.compose.material3.NavigationDrawerItem(
        label = {
            Text(
                text = item.navLabel,
                style = MaterialTheme.typography.titleMedium
            )
        },
        icon = {
            Icon(
                imageVector = item.getIcon(),
                contentDescription = item.iconDescription
            )
        },
        selected = selected,
        onClick = { onLogout() },
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = Color.Transparent,
            unselectedIconColor = LogoutColor,
            unselectedTextColor = LogoutColor
        )
    )
}
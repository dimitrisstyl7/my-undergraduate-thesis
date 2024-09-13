package gr.unipi.thesis.dimstyl.ui.components.modalDrawerSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gr.unipi.thesis.dimstyl.ui.navigation.NavRoute
import gr.unipi.thesis.dimstyl.ui.navigation.modalDrawerSheetItems
import gr.unipi.thesis.dimstyl.ui.screens.main.MainState
import gr.unipi.thesis.dimstyl.ui.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.ui.theme.TopBarColor

@Composable
fun ModalDrawerSheet(
    state: MainState,
    onNavigate: (NavRoute, String) -> Unit,
    onLogout: () -> Unit
) {
    androidx.compose.material3.ModalDrawerSheet(
        drawerContainerColor = LeftBarColor,
        modifier = Modifier.width(300.dp)
    ) {
        Row(
            Modifier
                .height(63.dp)
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dietitian",
                color = LeftBarColor,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Hub",
                color = TopBarColor,
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Column {
            var item = modalDrawerSheetItems.first()
            var selected = state.currentNavRoute == item.route

            NavigationDrawerItem(
                modifier = Modifier.padding(top = 10.dp),
                item = item,
                selected = selected
            ) {
                onNavigate(it, modalDrawerSheetItems.first().topBarTitle)
            }
            HorizontalDivider()
            for (i in 1 until modalDrawerSheetItems.size) {
                item = modalDrawerSheetItems[i]
                selected =
                    state.currentNavRoute == item.route
                            && state.currentNavRoute != NavRoute.LOGOUT

                if (i < modalDrawerSheetItems.size - 1) {
                    NavigationDrawerItem(
                        item = item,
                        selected = selected,
                        onNavigate = { onNavigate(it, item.topBarTitle) }
                    )
                } else {
                    HorizontalDivider()
                    NavigationDrawerItem(
                        item = item,
                        selected = selected,
                        onLogout = { onLogout() }
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ModalDrawerSheetPreview() {
    ModalDrawerSheet(MainState(), { _, _ -> }, {})
}

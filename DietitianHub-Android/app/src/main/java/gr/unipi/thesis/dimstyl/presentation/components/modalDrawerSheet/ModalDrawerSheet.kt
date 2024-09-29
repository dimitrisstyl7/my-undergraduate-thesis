package gr.unipi.thesis.dimstyl.presentation.components.modalDrawerSheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gr.unipi.thesis.dimstyl.presentation.navigation.NavRoute
import gr.unipi.thesis.dimstyl.presentation.navigation.modalDrawerSheetItems
import gr.unipi.thesis.dimstyl.presentation.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.presentation.theme.TopBarColor
import gr.unipi.thesis.dimstyl.presentation.utils.ContentType

@Composable
fun ModalDrawerSheet(
    currentNavRoute: NavRoute,
    onNavigate: (NavRoute, String) -> Unit,
    onLogout: () -> Unit
) {
    androidx.compose.material3.ModalDrawerSheet(
        drawerContainerColor = LeftBarColor,
        modifier = Modifier.width(300.dp)
    ) {
        LazyColumn {
            item(contentType = ContentType.MODAL_DRAWER_SHEET_TITLE) {
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
            }
            item(contentType = ContentType.MODAL_DRAWER_SHEET_NAV_ITEMS) {
                Column {
                    var item = modalDrawerSheetItems.first()
                    var selected = currentNavRoute == item.route

                    NavigationDrawerItem(
                        modifier = Modifier.padding(top = 10.dp),
                        item = item,
                        selected = selected,
                        onNavigate = onNavigate
                    )
                    HorizontalDivider()
                    for (i in 1 until modalDrawerSheetItems.size) {
                        item = modalDrawerSheetItems[i]
                        selected =
                            currentNavRoute == item.route && currentNavRoute != NavRoute.LOGOUT

                        if (i < modalDrawerSheetItems.size - 1) {
                            NavigationDrawerItem(
                                item = item,
                                selected = selected,
                                onNavigate = onNavigate
                            )
                        } else {
                            HorizontalDivider()
                            NavigationDrawerItem(
                                item = item,
                                selected = selected,
                                onLogout = onLogout
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ModalDrawerSheetPreview() {
    ModalDrawerSheet(NavRoute.ARTICLES, { _, _ -> }, {})
}

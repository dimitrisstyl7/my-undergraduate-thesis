package gr.unipi.thesis.dimstyl.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import gr.unipi.thesis.dimstyl.ui.theme.TopBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, onExpandMenu: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            IconButton(onClick = onExpandMenu) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = "Expand menu"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = TopBarColor,
            navigationIconContentColor = Color.Black,
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Black
        )
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar("TopBar") {}
}

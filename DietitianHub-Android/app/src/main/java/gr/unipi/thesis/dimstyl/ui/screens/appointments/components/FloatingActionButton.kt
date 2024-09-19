package gr.unipi.thesis.dimstyl.ui.screens.appointments.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import gr.unipi.thesis.dimstyl.ui.theme.PrimaryColor

@Composable
fun FloatingActionButton(modifier: Modifier, onClick: () -> Unit) {
    androidx.compose.material3.FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = PrimaryColor,
        contentColor = Color.White
    ) {
        Icon(Icons.Rounded.Add, "Create new appointment")
    }
}
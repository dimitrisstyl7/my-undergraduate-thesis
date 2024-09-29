package gr.unipi.thesis.dimstyl.presentation.components.circularProgressIndicators

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import gr.unipi.thesis.dimstyl.presentation.theme.PrimaryColor

@Composable
fun ButtonCircularProgressIndicator() {
    Column {
        androidx.compose.material3.CircularProgressIndicator(
            color = PrimaryColor,
            trackColor = Color.Transparent
        )
    }
}
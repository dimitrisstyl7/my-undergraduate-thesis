package gr.unipi.thesis.dimstyl.ui.components.circularProgressIndicators

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import gr.unipi.thesis.dimstyl.ui.theme.PrimaryColor

@Composable
fun ScreenCircularProgressIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        androidx.compose.material3.CircularProgressIndicator(
            color = PrimaryColor,
            trackColor = Color.Transparent
        )
    }
}
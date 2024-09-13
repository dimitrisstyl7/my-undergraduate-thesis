package gr.unipi.thesis.dimstyl.ui.components.modalDrawerSheet

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalDivider() {
    androidx.compose.material3.HorizontalDivider(
        modifier = Modifier.padding(
            vertical = 10.dp,
            horizontal = 18.dp
        ),
        thickness = 2.dp,
        color = Color.White
    )
}
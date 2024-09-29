package gr.unipi.thesis.dimstyl.presentation.screens.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import gr.unipi.thesis.dimstyl.presentation.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.presentation.theme.TopBarColor

@Composable
fun HorizontalDivider(
    maxWidth: Float = 0.75f,
    thickness: Dp = 3.dp,
    colors: List<Color> = listOf(LeftBarColor, TopBarColor)
) {
    Canvas(
        Modifier
            .height(thickness)
            .fillMaxWidth(maxWidth)
    ) {
        drawLine(
            brush = Brush.linearGradient(colors),
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = (thickness).toPx()
        )
    }
}
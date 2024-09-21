package gr.unipi.thesis.dimstyl.ui.components.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.TextCell(
    weight: Float,
    text: String,
    fontWeight: FontWeight,
    textColor: Color,
    cellColor: Color
) {
    Text(
        modifier = Modifier
            .background(cellColor)
            .height(45.dp)
            .weight(weight)
            .padding(8.dp),
        text = text,
        color = textColor,
        fontWeight = fontWeight,
        textAlign = TextAlign.Center
    )
}
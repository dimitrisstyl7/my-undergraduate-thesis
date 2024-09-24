package gr.unipi.thesis.dimstyl.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier,
    color: Color = Color.Black,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    fontWeight: FontWeight,
    textAlign: TextAlign
) {
    Text(
        text = title,
        modifier = modifier,
        color = color,
        style = style,
        fontWeight = fontWeight,
        textAlign = textAlign
    )
}
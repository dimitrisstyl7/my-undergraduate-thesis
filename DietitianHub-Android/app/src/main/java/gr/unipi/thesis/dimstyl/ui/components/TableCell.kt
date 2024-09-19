package gr.unipi.thesis.dimstyl.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import gr.unipi.thesis.dimstyl.ui.theme.TableCellColor
import gr.unipi.thesis.dimstyl.ui.theme.TableTextColor

@Composable
fun RowScope.TableCell(
    weight: Float,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    textColor: Color = TableTextColor,
    cellColor: Color = TableCellColor
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

@Composable
fun RowScope.IconTableCell(
    weight: Float,
    buttonColor: Color,
    icon: @Composable RowScope.() -> Unit,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(45.dp)
            .weight(weight),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = TableCellColor,
            contentColor = buttonColor
        ),
        content = { icon() }
    )
}
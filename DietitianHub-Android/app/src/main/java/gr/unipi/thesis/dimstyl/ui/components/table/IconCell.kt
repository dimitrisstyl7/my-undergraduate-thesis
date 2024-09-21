package gr.unipi.thesis.dimstyl.ui.components.table

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import gr.unipi.thesis.dimstyl.ui.theme.TableCellColor

@Composable
fun RowScope.IconCell(
    weight: Float,
    icon: @Composable RowScope.() -> Unit,
    buttonColor: Color,
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
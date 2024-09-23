package gr.unipi.thesis.dimstyl.ui.components.table

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

fun <T> createActionableTableRowsData(
    items: List<T>,
    getText: (T) -> String,
    icon: (T) -> (@Composable RowScope.() -> Unit),
    buttonColor: Color,
    onClick: () -> Unit
): List<List<CellData>> {
    return items.mapIndexed { index, item ->
        listOf(
            TextCellData(
                weight = 0.2f,
                text = (index + 1).toString(),
                fontWeight = FontWeight.Bold
            ),
            TextCellData(weight = 0.5f, text = getText(item)),
            IconCellData(
                weight = 0.3f,
                icon = icon(item),
                buttonColor = buttonColor,
                onClick = onClick
            )
        )
    }
}

fun <T> createSimpleTableRowsData(
    items: List<T>,
    getText: (T) -> String
): List<List<TextCellData>> {
    return items.mapIndexed { index, item ->
        listOf(
            TextCellData(
                weight = 0.2f,
                text = (index + 1).toString(),
                fontWeight = FontWeight.Bold
            ),
            TextCellData(weight = 0.5f, text = getText(item)),
            TextCellData(weight = 0.3f, text = "-")
        )
    }
}
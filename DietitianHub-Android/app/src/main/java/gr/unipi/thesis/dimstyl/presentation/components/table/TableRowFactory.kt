package gr.unipi.thesis.dimstyl.presentation.components.table

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import gr.unipi.thesis.dimstyl.data.models.Appointment
import gr.unipi.thesis.dimstyl.data.models.DietPlan

fun <T> createTableRowsData(
    cellsWeight: List<Float>,
    items: List<T>,
    getText: (T) -> String,
    icon: (T) -> (@Composable RowScope.() -> Unit),
    buttonColor: Color,
    isActionable: Boolean = true,
    onClick: (Int) -> Unit
): List<List<CellData>> {
    val rows = mutableListOf<List<CellData>>()

    items.mapIndexed { itemIndex, item ->
        val cells = mutableListOf<CellData>()

        cellsWeight.forEachIndexed { weightIndex, weight ->
            when (weightIndex) {
                // First cell contains the current row number (#)
                0 -> cells.add(
                    TextCellData(
                        weight = weight,
                        text = (itemIndex + 1).toString(),
                        fontWeight = FontWeight.Bold
                    )
                )

                // Last cell contains the action icon button (if the table is actionable),
                // else it contains a dash ('-'), indicating no action is available.
                cellsWeight.size - 1 -> cells.add(
                    when {
                        isActionable -> {
                            val id = when (item) {
                                is Appointment -> item.id
                                is DietPlan -> item.id
                                else -> -1
                            }

                            IconCellData(
                                weight = weight,
                                icon = icon(item),
                                buttonColor = buttonColor,
                                onClick = { onClick(id) }
                            )
                        }

                        else -> TextCellData(weight = weight, text = "-")
                    }
                )

                // All other cells contains the row data
                else -> cells.add(TextCellData(weight = weight, text = getText(item)))
            }
        }
        rows.add(cells)
    }

    return rows
}

fun createEmptyTableRowData(text: String): List<List<TextCellData>> {
    return listOf(
        listOf(
            TextCellData(weight = 1f, text = text)
        )
    )
}
package gr.unipi.thesis.dimstyl.ui.components.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import gr.unipi.thesis.dimstyl.ui.helpers.ContentType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Table(
    modifier: Modifier,
    headerCells: List<HeaderCellData>,
    tableRows: List<List<CellData>>
) {
    LazyColumn(modifier) {
        stickyHeader(contentType = ContentType.TABLE_HEADER) {
            Row {
                headerCells.forEach { cell ->
                    TextCell(
                        weight = cell.weight,
                        text = cell.text,
                        fontWeight = cell.fontWeight,
                        textColor = cell.textColor,
                        cellColor = cell.cellColor
                    )
                }
            }
        }

        tableRows.forEach { row ->
            item(contentType = ContentType.TABLE_BODY_ROW) {
                Row {
                    row.forEach { cell ->
                        when (cell) {
                            is TextCellData -> {
                                TextCell(
                                    weight = cell.weight,
                                    text = cell.text,
                                    fontWeight = cell.fontWeight,
                                    textColor = cell.textColor,
                                    cellColor = cell.cellColor
                                )

                            }

                            is IconCellData -> {
                                IconCell(
                                    weight = cell.weight,
                                    icon = cell.icon,
                                    buttonColor = cell.buttonColor,
                                    onClick = cell.onClick
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
package gr.unipi.thesis.dimstyl.presentation.components.table

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gr.unipi.thesis.dimstyl.presentation.theme.TableBorderColor
import gr.unipi.thesis.dimstyl.presentation.theme.TableHeaderBackgroundColor
import gr.unipi.thesis.dimstyl.presentation.utils.ContentType

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Table(
    modifier: Modifier,
    headerCells: List<HeaderCellData>,
    tableRows: List<List<CellData>>
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth(0.95f)
                .border(
                    width = 2.dp,
                    color = TableBorderColor,
                    shape = RoundedCornerShape(6.dp)
                )
        ) {
            stickyHeader(contentType = ContentType.TABLE_HEADER) {
                Row(
                    Modifier.background(
                        color = TableHeaderBackgroundColor,
                        shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
                    )
                ) {
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
}
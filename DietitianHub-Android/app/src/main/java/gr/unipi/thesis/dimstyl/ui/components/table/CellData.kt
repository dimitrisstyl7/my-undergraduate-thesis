package gr.unipi.thesis.dimstyl.ui.components.table

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import gr.unipi.thesis.dimstyl.ui.theme.TableCellColor
import gr.unipi.thesis.dimstyl.ui.theme.TableHeaderCellColor
import gr.unipi.thesis.dimstyl.ui.theme.TableHeaderColor
import gr.unipi.thesis.dimstyl.ui.theme.TableTextColor

abstract class CellData(val weight: Float)

open class TextCellData(
    weight: Float,
    val text: String,
    val fontWeight: FontWeight = FontWeight.Normal,
    val textColor: Color = TableTextColor,
    val cellColor: Color = TableCellColor
) : CellData(weight)

class HeaderCellData(
    weight: Float,
    text: String,
    fontWeight: FontWeight = FontWeight.Bold,
    textColor: Color = TableHeaderColor,
    cellColor: Color = TableHeaderCellColor
) : TextCellData(weight, text, fontWeight, textColor, cellColor)

class IconCellData(
    weight: Float,
    val icon: @Composable RowScope.() -> Unit,
    val buttonColor: Color,
    val onClick: () -> Unit
) : CellData(weight)
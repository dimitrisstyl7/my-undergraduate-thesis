package gr.unipi.thesis.dimstyl.ui.screens.dietPlans

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import gr.unipi.thesis.dimstyl.ui.helpers.ContentType
import gr.unipi.thesis.dimstyl.ui.screens.dietPlans.components.DownloadIconTableCell
import gr.unipi.thesis.dimstyl.ui.screens.dietPlans.components.TableCell
import gr.unipi.thesis.dimstyl.ui.theme.BodyColor
import gr.unipi.thesis.dimstyl.ui.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.ui.theme.TableHeaderColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DietPlansScreen() {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(BodyColor)
    ) {
        stickyHeader(contentType = ContentType.DIET_PLANS_TABLE_HEADER) {
            Row {
                TableCell(
                    weight = 0.2f,
                    text = "#",
                    fontWeight = FontWeight.Bold,
                    textColor = TableHeaderColor,
                    cellColor = LeftBarColor
                )

                TableCell(
                    weight = 0.5f,
                    text = "Created on",
                    fontWeight = FontWeight.Bold,
                    textColor = TableHeaderColor,
                    cellColor = LeftBarColor
                )

                TableCell(
                    weight = 0.3f,
                    text = "Actions",
                    fontWeight = FontWeight.Bold,
                    textColor = TableHeaderColor,
                    cellColor = LeftBarColor
                )
            }
        }

        itemsIndexed(
            items = dietPlans,
            contentType = { _, _ -> ContentType.DIET_PLANS_TABLE_BODY }) { index, dietPlan ->
            Row {
                TableCell(
                    weight = 0.2f,
                    text = (index + 1).toString(),
                    fontWeight = FontWeight.Bold
                )
                TableCell(weight = 0.5f, text = dietPlan.createdOn)
                DownloadIconTableCell(weight = 0.3f)
            }
            HorizontalDivider()
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DietPlansScreenPreview() {
    DietPlansScreen()
}
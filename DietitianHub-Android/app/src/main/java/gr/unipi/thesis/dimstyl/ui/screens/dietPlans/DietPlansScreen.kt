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

// Start of temporary data
data class DietPlan(
    val id: Int,
    val createdOn: String
)

val dietPlans = listOf(
    DietPlan(24, "3 Jan 2023"),
    DietPlan(25, "3 Feb 2023"),
    DietPlan(26, "3 Mar 2023"),
    DietPlan(27, "3 Apr 2023"),
    DietPlan(28, "3 May 2023"),
    DietPlan(29, "3 Jun 2023"),
    DietPlan(30, "3 Jul 2023"),
    DietPlan(31, "3 Aug 2023"),
    DietPlan(32, "3 Sep 2023"),
    DietPlan(33, "3 Oct 2023"),
    DietPlan(34, "3 Nov 2023"),
    DietPlan(35, "3 Dec 2023"),
    DietPlan(36, "3 Jan 2024"),
    DietPlan(37, "3 Feb 2024"),
    DietPlan(38, "3 Mar 2024"),
    DietPlan(39, "3 Apr 2024"),
    DietPlan(40, "3 May 2024"),
    DietPlan(41, "3 Jun 2024"),
)
// End of temporary data

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DietPlansScreenPreview() {
    DietPlansScreen()
}
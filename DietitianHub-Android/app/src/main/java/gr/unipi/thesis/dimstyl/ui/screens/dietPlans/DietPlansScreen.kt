package gr.unipi.thesis.dimstyl.ui.screens.dietPlans

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import gr.unipi.thesis.dimstyl.R
import gr.unipi.thesis.dimstyl.ui.components.CircularProgressIndicator
import gr.unipi.thesis.dimstyl.ui.components.IconTableCell
import gr.unipi.thesis.dimstyl.ui.components.TableCell
import gr.unipi.thesis.dimstyl.ui.helpers.ContentType
import gr.unipi.thesis.dimstyl.ui.theme.BodyColor
import gr.unipi.thesis.dimstyl.ui.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.ui.theme.PrimaryColor
import gr.unipi.thesis.dimstyl.ui.theme.TableHeaderColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DietPlansScreen(viewModel: DietPlansViewModel = viewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isLoading) {
        CircularProgressIndicator()
    } else {
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
                        text = "Created On",
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
                items = state.dietPlans,
                contentType = { _, _ -> ContentType.DIET_PLANS_TABLE_BODY }) { index, dietPlan ->
                Row {
                    TableCell(
                        weight = 0.2f,
                        text = (index + 1).toString(),
                        fontWeight = FontWeight.Bold
                    )
                    TableCell(weight = 0.5f, text = dietPlan.createdOn)
                    IconTableCell(
                        weight = 0.3f,
                        buttonColor = PrimaryColor,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.baseline_download_24),
                                contentDescription = "Download the diet plan created on ${dietPlan.createdOn}"
                            )
                        },
                        onClick = { /* TODO: Download the diet plan */ }
                    )
                }
                HorizontalDivider()
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DietPlansScreenPreview() {
    DietPlansScreen()
}
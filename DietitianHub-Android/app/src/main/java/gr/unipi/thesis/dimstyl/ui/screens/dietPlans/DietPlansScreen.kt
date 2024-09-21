package gr.unipi.thesis.dimstyl.ui.screens.dietPlans

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
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
import gr.unipi.thesis.dimstyl.ui.components.table.CellData
import gr.unipi.thesis.dimstyl.ui.components.table.IconCellData
import gr.unipi.thesis.dimstyl.ui.components.table.Table
import gr.unipi.thesis.dimstyl.ui.components.table.TextCellData
import gr.unipi.thesis.dimstyl.ui.theme.BodyColor
import gr.unipi.thesis.dimstyl.ui.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.ui.theme.PrimaryColor
import gr.unipi.thesis.dimstyl.ui.theme.TableHeaderColor

@Composable
fun DietPlansScreen(
    viewModel: DietPlansViewModel = viewModel(),
    backHandler: @Composable () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    backHandler()

    if (state.isLoading) {
        CircularProgressIndicator()
    } else {
        val headerCells = listOf(
            TextCellData(
                weight = 0.2f,
                text = "#",
                fontWeight = FontWeight.Bold,
                textColor = TableHeaderColor,
                cellColor = LeftBarColor
            ),
            TextCellData(
                weight = 0.5f,
                text = "Created On",
                fontWeight = FontWeight.Bold,
                textColor = TableHeaderColor,
                cellColor = LeftBarColor
            ),
            TextCellData(
                weight = 0.3f,
                text = "Actions",
                fontWeight = FontWeight.Bold,
                textColor = TableHeaderColor,
                cellColor = LeftBarColor
            )
        )
        val tableRows: List<List<CellData>> = state.dietPlans.mapIndexed { index, dietPlan ->
            listOf(
                TextCellData(
                    weight = 0.2f,
                    text = (index + 1).toString(),
                    fontWeight = FontWeight.Bold
                ),
                TextCellData(weight = 0.5f, text = dietPlan.createdOn),
                IconCellData(
                    weight = 0.3f,
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.baseline_download_24),
                            contentDescription = "Download the diet plan created on ${dietPlan.createdOn}"
                        )
                    },
                    buttonColor = PrimaryColor,
                    onClick = { /* TODO: Download the diet plan */ }
                )
            )
        }

        Table(
            modifier = Modifier
                .fillMaxSize()
                .background(BodyColor),
            headerCells = headerCells,
            tableRows = tableRows
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DietPlansScreenPreview() {
    DietPlansScreen {}
}
package gr.unipi.thesis.dimstyl.ui.screens.dietPlans

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import gr.unipi.thesis.dimstyl.ui.components.CircularProgressIndicator
import gr.unipi.thesis.dimstyl.ui.components.table.Table
import gr.unipi.thesis.dimstyl.ui.theme.BodyColor

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
        Table(
            modifier = Modifier
                .background(BodyColor)
                .padding(vertical = 16.dp),
            headerCells = viewModel.tableHeaderCellsData,
            tableRows = state.tableRowsData
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DietPlansScreenPreview() {
    DietPlansScreen {}
}
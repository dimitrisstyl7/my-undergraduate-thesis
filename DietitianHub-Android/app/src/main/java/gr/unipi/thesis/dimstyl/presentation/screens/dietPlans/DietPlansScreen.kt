package gr.unipi.thesis.dimstyl.presentation.screens.dietPlans

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import gr.unipi.thesis.dimstyl.App
import gr.unipi.thesis.dimstyl.presentation.components.circularProgressIndicators.ScreenCircularProgressIndicator
import gr.unipi.thesis.dimstyl.presentation.components.table.Table
import gr.unipi.thesis.dimstyl.presentation.theme.BodyColor
import gr.unipi.thesis.dimstyl.presentation.utils.viewModelFactory

@Composable
fun DietPlansScreen(
    viewModel: DietPlansViewModel = viewModel<DietPlansViewModel>(
        factory = viewModelFactory {
            DietPlansViewModel(
                App.appModule.fetchDietPlansUseCase,
                App.appModule.downloadDietPlanUseCase
            )
        }
    ),
    onSnackbarShow: (String, Boolean) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isLoading) {
        ScreenCircularProgressIndicator()
        LaunchedEffect(Unit) {
            viewModel.fetchDietPlans(
                onFetchDietPlansResult = { message, shortDuration ->
                    onSnackbarShow(message, shortDuration)
                }
            )
        }
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
    DietPlansScreen { _, _ -> }
}
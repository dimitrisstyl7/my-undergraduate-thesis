package gr.unipi.thesis.dimstyl.presentation.screens.dietPlans

import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.unipi.thesis.dimstyl.R
import gr.unipi.thesis.dimstyl.data.models.DietPlan
import gr.unipi.thesis.dimstyl.domain.usecases.DownloadDietPlanUseCase
import gr.unipi.thesis.dimstyl.domain.usecases.FetchDietPlansUseCase
import gr.unipi.thesis.dimstyl.presentation.components.table.CellData
import gr.unipi.thesis.dimstyl.presentation.components.table.HeaderCellData
import gr.unipi.thesis.dimstyl.presentation.components.table.createEmptyTableRowData
import gr.unipi.thesis.dimstyl.presentation.components.table.createTableRowsData
import gr.unipi.thesis.dimstyl.presentation.theme.PrimaryColor
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.FETCH_DIET_PLANS_ERROR_MESSAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DietPlansViewModel(
    private val fetchDietPlansUseCase: FetchDietPlansUseCase,
    private val downloadDietPlanUseCase: DownloadDietPlanUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DietPlansState())
    val state = _state.asStateFlow()

    private val cellsWeight = listOf(0.2f, 0.6f, 0.2f)
    lateinit var tableHeaderCellsData: List<HeaderCellData>

    init {
        initializeTableHeaderCellsData()
    }

    private fun initializeTableHeaderCellsData() {
        tableHeaderCellsData = listOf(
            HeaderCellData(weight = cellsWeight[0], text = "#"),
            HeaderCellData(weight = cellsWeight[1], text = "Created On"),
            HeaderCellData(weight = cellsWeight[2], text = "Actions")
        )
    }

    fun fetchDietPlans(onFetchDietPlansResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val result = fetchDietPlansUseCase()
            val dietPlans = result.getOrNull()
            var tableRowsData: List<List<CellData>> =
                createEmptyTableRowData("Diet Plans not found")

            if (result.isSuccess && dietPlans != null) {
                tableRowsData = createTableRowsData(dietPlans)
            } else {
                val errorMessage =
                    result.exceptionOrNull()?.message ?: FETCH_DIET_PLANS_ERROR_MESSAGE
                onFetchDietPlansResult(errorMessage, false)
            }

            _state.value = _state.value.copy(tableRowsData = tableRowsData, isLoading = false)
        }
    }

    private fun createTableRowsData(dietPlans: List<DietPlan>): List<List<CellData>> {
        return if (dietPlans.isEmpty()) createEmptyTableRowData("No diet plans found")
        else createTableRowsData(
            cellsWeight = cellsWeight,
            items = dietPlans,
            getText = { dietPlan -> dietPlan.createdOn },
            icon = { dietPlan ->
                {
                    Icon(
                        painter = painterResource(R.drawable.baseline_download_24),
                        contentDescription = "Download the diet plan created on ${dietPlan.createdOn}"
                    )
                }
            },
            buttonColor = PrimaryColor,
            onClick = { id ->
                val dietPlan = dietPlans.find { it.id == id }
                val fileName =
                    if (dietPlan != null) "diet plan (${dietPlan.createdOn}).pdf"
                    else "diet plan $id.pdf"
                downloadDietPlanUseCase(id, fileName)
            }
        )
    }

}
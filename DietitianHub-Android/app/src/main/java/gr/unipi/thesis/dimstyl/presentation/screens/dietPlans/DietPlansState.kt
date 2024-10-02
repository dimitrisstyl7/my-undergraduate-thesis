package gr.unipi.thesis.dimstyl.presentation.screens.dietPlans

import gr.unipi.thesis.dimstyl.presentation.components.table.CellData

data class DietPlansState(
    val tableRowsData: List<List<CellData>> = emptyList(),
    val isLoading: Boolean = true
)
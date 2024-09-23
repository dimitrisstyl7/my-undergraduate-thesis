package gr.unipi.thesis.dimstyl.ui.screens.dietPlans

import gr.unipi.thesis.dimstyl.ui.components.table.CellData

data class DietPlansState(
    val tableRowsData: List<List<CellData>> = emptyList(),
    val isLoading: Boolean = false
)
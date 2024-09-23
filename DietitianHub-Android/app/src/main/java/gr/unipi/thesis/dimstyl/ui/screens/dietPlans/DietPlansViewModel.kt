package gr.unipi.thesis.dimstyl.ui.screens.dietPlans

import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import gr.unipi.thesis.dimstyl.R
import gr.unipi.thesis.dimstyl.data.model.DietPlan
import gr.unipi.thesis.dimstyl.ui.components.table.HeaderCellData
import gr.unipi.thesis.dimstyl.ui.components.table.createActionableTableRowsData
import gr.unipi.thesis.dimstyl.ui.theme.PrimaryColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DietPlansViewModel : ViewModel() {

    private val _state = MutableStateFlow(DietPlansState())
    val state = _state.asStateFlow()

    lateinit var tableHeaderCellsData: List<HeaderCellData>

    init {
        initializeTableHeaderCellsData()
        fetchDietPlans()
    }

    private fun initializeTableHeaderCellsData() {
        tableHeaderCellsData = listOf(
            HeaderCellData(weight = 0.2f, text = "#"),
            HeaderCellData(weight = 0.5f, text = "Created On"),
            HeaderCellData(weight = 0.3f, text = "Actions")
        )
    }

    private fun fetchDietPlans() {
        _state.value = _state.value.copy(isLoading = true)

        // TODO: Fetch diet plans from the server
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
            DietPlan(41, "3 Jun 2024")
        )

        val tableRowsData = createActionableTableRowsData(
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
            onClick = { /* TODO: Download the diet plan */ }
        )

        _state.value = _state.value.copy(tableRowsData = tableRowsData, isLoading = false)
    }

}
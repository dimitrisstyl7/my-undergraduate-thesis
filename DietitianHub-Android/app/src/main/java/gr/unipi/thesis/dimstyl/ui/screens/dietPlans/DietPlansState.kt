package gr.unipi.thesis.dimstyl.ui.screens.dietPlans

import gr.unipi.thesis.dimstyl.data.model.DietPlan

data class DietPlansState(val dietPlans: List<DietPlan> = emptyList(), val isLoading: Boolean = false)
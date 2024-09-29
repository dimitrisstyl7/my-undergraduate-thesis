package gr.unipi.thesis.dimstyl.presentation.screens.home

import gr.unipi.thesis.dimstyl.data.models.Announcement
import gr.unipi.thesis.dimstyl.data.models.Article
import gr.unipi.thesis.dimstyl.presentation.components.table.CellData

data class HomeState(
    val fullName: String? = null,
    val articles: List<Article> = emptyList(),
    val announcements: List<Announcement> = emptyList(),
    val appointmentsTableRowsData: List<List<CellData>> = emptyList(),
    val isLoading: Boolean = false
)

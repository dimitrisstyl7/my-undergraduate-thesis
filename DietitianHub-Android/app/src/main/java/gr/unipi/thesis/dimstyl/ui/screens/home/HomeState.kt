package gr.unipi.thesis.dimstyl.ui.screens.home

import gr.unipi.thesis.dimstyl.data.model.Announcement
import gr.unipi.thesis.dimstyl.data.model.Article
import gr.unipi.thesis.dimstyl.ui.components.table.CellData

data class HomeState(
    val fullName: String? = null,
    val articles: List<Article> = emptyList(),
    val announcements: List<Announcement> = emptyList(),
    val appointmentsTableRowsData: List<List<CellData>> = emptyList(),
    val isLoading: Boolean = false
)

package gr.unipi.thesis.dimstyl.ui.screens.announcements

import gr.unipi.thesis.dimstyl.data.model.AnnouncementSection

data class AnnouncementsState(
    val announcementSections: List<AnnouncementSection> = emptyList(),
    val isLoading: Boolean = false
)
package gr.unipi.thesis.dimstyl.presentation.screens.announcements

import gr.unipi.thesis.dimstyl.domain.models.AnnouncementSection

data class AnnouncementsState(
    val announcementSections: List<AnnouncementSection> = emptyList(),
    val isLoading: Boolean = false
)
package gr.unipi.thesis.dimstyl.presentation.screens.announcements

import gr.unipi.thesis.dimstyl.presentation.models.AnnouncementSectionWithColor

data class AnnouncementsState(
    val announcementSections: List<AnnouncementSectionWithColor> = emptyList(),
    val isLoading: Boolean = true
)
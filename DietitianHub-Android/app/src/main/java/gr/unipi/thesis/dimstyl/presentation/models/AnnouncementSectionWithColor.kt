package gr.unipi.thesis.dimstyl.presentation.models

import androidx.compose.ui.graphics.Color
import gr.unipi.thesis.dimstyl.data.models.Announcement

data class AnnouncementSectionWithColor(
    val title: String,
    val announcements: List<Announcement>,
    val color: Color
)

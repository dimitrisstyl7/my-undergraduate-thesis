package gr.unipi.thesis.dimstyl.domain.models

import androidx.compose.ui.graphics.Color
import gr.unipi.thesis.dimstyl.data.models.Announcement

data class AnnouncementSection(
    val title: String,
    val announcements: List<Announcement>,
    val color: Color
)
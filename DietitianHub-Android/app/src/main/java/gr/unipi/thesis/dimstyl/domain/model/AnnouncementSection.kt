package gr.unipi.thesis.dimstyl.domain.model

import androidx.compose.ui.graphics.Color
import gr.unipi.thesis.dimstyl.data.model.Announcement

data class AnnouncementSection(
    val title: String,
    val announcements: List<Announcement>,
    val color: Color
)
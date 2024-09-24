package gr.unipi.thesis.dimstyl.data.model

import androidx.compose.ui.graphics.Color

data class AnnouncementSection(
    val title: String,
    val announcements: List<Announcement>,
    val color: Color
)
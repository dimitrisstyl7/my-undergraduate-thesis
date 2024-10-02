package gr.unipi.thesis.dimstyl.domain.models

import androidx.compose.ui.graphics.Color
import gr.unipi.thesis.dimstyl.data.models.Announcement
import gr.unipi.thesis.dimstyl.presentation.models.AnnouncementSectionWithColor

data class AnnouncementSection(val title: String, val announcements: List<Announcement>) {

    fun toAnnouncementSectionsWithColor(color: Color): AnnouncementSectionWithColor {
        return AnnouncementSectionWithColor(title, announcements, color)
    }

}
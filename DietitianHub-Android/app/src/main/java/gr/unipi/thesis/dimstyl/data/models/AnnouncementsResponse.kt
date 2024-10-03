package gr.unipi.thesis.dimstyl.data.models

import gr.unipi.thesis.dimstyl.domain.models.Announcement
import gr.unipi.thesis.dimstyl.domain.models.AnnouncementSection

data class AnnouncementsResponse(
    val announcementsForToday: List<Announcement>,
    val announcementsForYesterday: List<Announcement>,
    val earlierAnnouncements: List<Announcement>
) {

    fun toAnnouncementSections(): List<AnnouncementSection> {
        return listOf(
            AnnouncementSection(title = "Today", announcementsForToday),
            AnnouncementSection(title = "Yesterday", announcementsForYesterday),
            AnnouncementSection(title = "Earlier", earlierAnnouncements)
        )
    }

}

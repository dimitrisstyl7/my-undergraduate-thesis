package gr.unipi.thesis.dimstyl.presentation.screens.announcements

import androidx.lifecycle.ViewModel
import gr.unipi.thesis.dimstyl.data.models.Announcement
import gr.unipi.thesis.dimstyl.domain.models.AnnouncementSection
import gr.unipi.thesis.dimstyl.presentation.theme.AnnouncementsFirstSectionBackgroundColor
import gr.unipi.thesis.dimstyl.presentation.theme.AnnouncementsSecondSectionBackgroundColor
import gr.unipi.thesis.dimstyl.presentation.theme.AnnouncementsThirdSectionBackgroundColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AnnouncementsViewModel : ViewModel() {

    private val _state = MutableStateFlow(AnnouncementsState())
    val state = _state.asStateFlow()

    init {
        fetchAnnouncements()
    }

    private fun fetchAnnouncements() {
        _state.value = _state.value.copy(isLoading = true)

        // TODO: Fetch appointments from the server
        val announcementSections = listOf(
            AnnouncementSection(
                "Today",
                listOf(
                    Announcement(
                        1,
                        "Announcement 1",
                        "15 Sep 2024, 07:07 PM"
                    ),
                    Announcement(
                        2,
                        "New Holidays ahead! get ready! Can't wait to see you all there!",
                        "15 Sep 2024, 07:07 PM"
                    ),
                    Announcement(
                        3,
                        "Announcement 3",
                        "15 Sep 2024, 07:07 PM"
                    ),
                    Announcement(
                        4,
                        "Announcement 4",
                        "15 Sep 2024, 07:07 PM"
                    )
                ),
                AnnouncementsFirstSectionBackgroundColor
            ),
            AnnouncementSection(
                "Yesterday",
                listOf(
                    Announcement(
                        5,
                        "Announcement 5",
                        "14 Sep 2024, 07:07 PM"
                    ),
                    Announcement(
                        6,
                        "Announcement 6",
                        "14 Sep 2024, 07:07 PM"
                    ),
                    Announcement(
                        7,
                        "Announcement 7",
                        "14 Sep 2024, 07:07 PM"
                    ),
                    Announcement(
                        8,
                        "Announcement 8",
                        "14 Sep 2024, 07:07 PM"
                    )
                ),
                AnnouncementsSecondSectionBackgroundColor
            ),
            AnnouncementSection(
                "Earlier",
                listOf(
                    Announcement(
                        9,
                        "Announcement 9",
                        "10 Sep 2024, 07:07 PM"
                    ),
                    Announcement(
                        10,
                        "Announcement 10",
                        "10 Sep 2024, 07:07 PM"
                    ),
                    Announcement(
                        11,
                        "Announcement 11",
                        "10 Sep 2024, 07:07 PM"
                    ),
                    Announcement(
                        12,
                        "Announcement 12",
                        "10 Sep 2024, 07:07 PM"
                    )
                ),
                AnnouncementsThirdSectionBackgroundColor
            ),
        )

        _state.value = _state.value.copy(announcementSections = announcementSections, isLoading = false)

    }

}
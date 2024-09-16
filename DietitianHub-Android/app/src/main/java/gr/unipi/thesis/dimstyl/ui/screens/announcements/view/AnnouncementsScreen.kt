package gr.unipi.thesis.dimstyl.ui.screens.announcements.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gr.unipi.thesis.dimstyl.ui.components.Card
import gr.unipi.thesis.dimstyl.ui.theme.AnnouncementSectionTitleColor
import gr.unipi.thesis.dimstyl.ui.theme.AnnouncementsFirstSectionBackgroundColor
import gr.unipi.thesis.dimstyl.ui.theme.AnnouncementsSecondSectionBackgroundColor
import gr.unipi.thesis.dimstyl.ui.theme.AnnouncementsThirdSectionBackgroundColor
import gr.unipi.thesis.dimstyl.ui.theme.CardTitleColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnnouncementsScreen() {
    LazyColumn(
        contentPadding = PaddingValues(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        sections.forEach { section ->
            stickyHeader(contentType = section) {
                Row(Modifier.padding(top = 16.dp)) {
                    Box(
                        modifier = Modifier
                            .size(width = 150.dp, height = 30.dp)
                            .border(
                                width = 1.dp,
                                color = section.backgroundColor,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .background(
                                color = section.backgroundColor,
                                shape = RoundedCornerShape(4.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = section.title, color = AnnouncementSectionTitleColor)
                    }
                }
            }

            item(contentType = section) {
                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val announcements = section.announcements

                    if (announcements.isEmpty()) {
                        item(contentType = "no_announcements") {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "No announcements",
                                fontWeight = FontWeight.SemiBold,
                                color = CardTitleColor
                            )
                        }
                    } else {
                        items(items = announcements, contentType = { it }) { announcement ->
                            Card(
                                title = announcement.title,
                                createdAt = announcement.createdAt,
                                createdAtColor = section.backgroundColor
                            )
                        }
                    }
                }
            }
        }
    }
}

// Temporary data, TODO: Remove this and use real data when available
val sections = listOf(
    Section(
        "Today",
        listOf(
            Announcement(
                "Announcement 1",
                "This is the content of announcement 1",
                "15 Sep 2024, 07:07 PM"
            ),
            Announcement(
                "New Holidays ahead! get ready! Can't wait to see you all there!",
                "This is the content of announcement 2",
                "15 Sep 2024, 07:07 PM"
            ),
            Announcement(
                "Announcement 3",
                "This is the content of announcement 3",
                "15 Sep 2024, 07:07 PM"
            ),
            Announcement(
                "Announcement 4",
                "This is the content of announcement 4",
                "15 Sep 2024, 07:07 PM"
            )
        ),
        AnnouncementsFirstSectionBackgroundColor
    ),
    Section(
        "Yesterday",
        listOf(
            Announcement(
                "Announcement 5",
                "This is the content of announcement 5",
                "14 Sep 2024, 07:07 PM"
            ),
            Announcement(
                "Announcement 6",
                "This is the content of announcement 6",
                "14 Sep 2024, 07:07 PM"
            ),
            Announcement(
                "Announcement 7",
                "This is the content of announcement 7",
                "14 Sep 2024, 07:07 PM"
            ),
            Announcement(
                "Announcement 8",
                "This is the content of announcement 8",
                "14 Sep 2024, 07:07 PM"
            )
        ),
        AnnouncementsSecondSectionBackgroundColor
    ),
    Section(
        "Earlier",
        listOf(
            Announcement(
                "Announcement 9",
                "This is the content of announcement 9",
                "10 Sep 2024, 07:07 PM"
            ),
            Announcement(
                "Announcement 10",
                "This is the content of announcement 10",
                "10 Sep 2024, 07:07 PM"
            ),
            Announcement(
                "Announcement 11",
                "This is the content of announcement 11",
                "10 Sep 2024, 07:07 PM"
            ),
            Announcement(
                "Announcement 12",
                "This is the content of announcement 12",
                "10 Sep 2024, 07:07 PM"
            )
        ),
        AnnouncementsThirdSectionBackgroundColor
    ),
)

data class Section(
    val title: String,
    val announcements: List<Announcement>,
    val backgroundColor: Color
)

data class Announcement(val title: String, val content: String, val createdAt: String)
// End of temporary data

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AnnouncementsScreenPreview() {
    AnnouncementsScreen()
}
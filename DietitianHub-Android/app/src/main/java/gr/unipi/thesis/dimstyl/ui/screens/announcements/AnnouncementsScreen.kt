package gr.unipi.thesis.dimstyl.ui.screens.announcements

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
import gr.unipi.thesis.dimstyl.ui.helpers.ContentType
import gr.unipi.thesis.dimstyl.ui.theme.AnnouncementSectionTitleColor
import gr.unipi.thesis.dimstyl.ui.theme.AnnouncementTitleColor
import gr.unipi.thesis.dimstyl.ui.theme.AnnouncementsFirstSectionBackgroundColor
import gr.unipi.thesis.dimstyl.ui.theme.AnnouncementsSecondSectionBackgroundColor
import gr.unipi.thesis.dimstyl.ui.theme.AnnouncementsThirdSectionBackgroundColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnnouncementsScreen() {
    LazyColumn(
        contentPadding = PaddingValues(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        sections.forEach { section ->
            stickyHeader(contentType = ContentType.ANNOUNCEMENTS_SECTION_TITLE) {
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

            item(contentType = ContentType.ANNOUNCEMENTS_SECTION_BODY) {
                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val announcements = section.announcements

                    if (announcements.isEmpty()) {
                        item(contentType = ContentType.NO_ANNOUNCEMENTS) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "No announcements",
                                fontWeight = FontWeight.SemiBold,
                                color = AnnouncementTitleColor
                            )
                        }
                    } else {
                        items(
                            items = announcements,
                            contentType = { ContentType.ANNOUNCEMENTS_SECTION_CARD }) { announcement ->
                            Card(
                                title = announcement.title,
                                createdAt = announcement.createdAt,
                                titleColor = AnnouncementTitleColor,
                                createdAtColor = section.backgroundColor
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AnnouncementsScreenPreview() {
    AnnouncementsScreen()
}
package gr.unipi.thesis.dimstyl.presentation.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import gr.unipi.thesis.dimstyl.data.models.Announcement
import gr.unipi.thesis.dimstyl.presentation.theme.AnnouncementTitleColor
import gr.unipi.thesis.dimstyl.presentation.theme.DataNotFoundColor
import gr.unipi.thesis.dimstyl.presentation.utils.ContentType

@Composable
fun AnnouncementsCardsRow(
    announcements: List<Announcement>,
    createdAtColor: Color,
    onClick: (Announcement) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (announcements.isEmpty()) {
            item(contentType = ContentType.NO_ANNOUNCEMENTS) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "No announcements found",
                    fontWeight = FontWeight.SemiBold,
                    color = DataNotFoundColor
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
                    createdAtColor = createdAtColor,
                    onClick = { onClick(announcement) }
                )
            }
        }
    }
}
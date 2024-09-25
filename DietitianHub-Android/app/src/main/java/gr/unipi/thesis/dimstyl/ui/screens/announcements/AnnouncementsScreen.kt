package gr.unipi.thesis.dimstyl.ui.screens.announcements

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import gr.unipi.thesis.dimstyl.ui.components.CircularProgressIndicator
import gr.unipi.thesis.dimstyl.ui.components.cards.AnnouncementsCardsRow
import gr.unipi.thesis.dimstyl.ui.helpers.ContentType
import gr.unipi.thesis.dimstyl.ui.theme.AnnouncementSectionTitleColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnnouncementsScreen(viewModel: AnnouncementsViewModel = viewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if (state.isLoading) {
        CircularProgressIndicator()
    } else {
        LazyColumn(
            contentPadding = PaddingValues(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            state.announcementSections.forEach { section ->
                stickyHeader(contentType = ContentType.ANNOUNCEMENTS_SECTION_TITLE) {
                    Row(Modifier.padding(top = 16.dp)) {
                        Box(
                            modifier = Modifier
                                .size(width = 150.dp, height = 30.dp)
                                .border(
                                    width = 1.dp,
                                    color = section.color,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .background(
                                    color = section.color,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = section.title, color = AnnouncementSectionTitleColor)
                        }
                    }
                }

                item(contentType = ContentType.ANNOUNCEMENTS_SECTION_BODY) {
                    AnnouncementsCardsRow(
                        announcements = section.announcements,
                        createdAtColor = section.color,
                        onClick = { announcement ->
                            // TODO: Handle announcement click
                            Toast.makeText(
                                context,
                                "Announcement clicked: ${announcement.title}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
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
package gr.unipi.thesis.dimstyl.presentation.screens.announcements

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import gr.unipi.thesis.dimstyl.App
import gr.unipi.thesis.dimstyl.presentation.components.cards.AnnouncementsCardsRow
import gr.unipi.thesis.dimstyl.presentation.components.circularProgressIndicators.ScreenCircularProgressIndicator
import gr.unipi.thesis.dimstyl.presentation.theme.AnnouncementSectionTitleColor
import gr.unipi.thesis.dimstyl.presentation.theme.DataNotFoundColor
import gr.unipi.thesis.dimstyl.presentation.utils.ContentType
import gr.unipi.thesis.dimstyl.presentation.utils.viewModelFactory

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnnouncementsScreen(
    viewModel: AnnouncementsViewModel = viewModel<AnnouncementsViewModel>(
        factory = viewModelFactory {
            AnnouncementsViewModel(App.appModule.fetchAnnouncementsUseCase)
        }
    ),
    onSnackbarShow: (String, Boolean) -> Unit,
    onShowWebView: (String, Boolean) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isLoading) {
        ScreenCircularProgressIndicator()
        LaunchedEffect(Unit) {
            viewModel.fetchAnnouncements(
                onFetchAnnouncementsResult = { message, shortDuration ->
                    onSnackbarShow(message, shortDuration)
                }
            )
        }
    } else if (state.announcementSections.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Announcements not found",
                fontWeight = FontWeight.SemiBold,
                color = DataNotFoundColor
            )
        }
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
                            val endpoint = "announcements/${announcement.id}/preview"
                            onShowWebView(endpoint, true)
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
    AnnouncementsScreen(onSnackbarShow = { _, _ -> }, onShowWebView = { _, _ -> })
}
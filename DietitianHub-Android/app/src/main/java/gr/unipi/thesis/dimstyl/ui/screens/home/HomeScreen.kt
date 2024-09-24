package gr.unipi.thesis.dimstyl.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import gr.unipi.thesis.dimstyl.ui.components.CircularProgressIndicator
import gr.unipi.thesis.dimstyl.ui.components.SectionTitle
import gr.unipi.thesis.dimstyl.ui.components.cards.AnnouncementsCardsRow
import gr.unipi.thesis.dimstyl.ui.components.cards.ArticleCard
import gr.unipi.thesis.dimstyl.ui.components.table.Table
import gr.unipi.thesis.dimstyl.ui.helpers.ContentType
import gr.unipi.thesis.dimstyl.ui.screens.home.components.HorizontalDivider
import gr.unipi.thesis.dimstyl.ui.theme.AnnouncementsFirstSectionBackgroundColor
import gr.unipi.thesis.dimstyl.ui.theme.DataNotFoundColor
import gr.unipi.thesis.dimstyl.ui.theme.LeftBarColor

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel(), backHandler: @Composable () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    backHandler()

    if (state.isLoading) {
        CircularProgressIndicator()
    } else {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item(contentType = ContentType.WELCOME_MESSAGE) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Welcome, ${state.fullName!!} !",
                        color = LeftBarColor,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider()
                }
            }

            item(contentType = ContentType.PERSONALIZED_ARTICLES) {
                SectionTitle(
                    title = "Articles you may like",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (state.articles.isEmpty()) {
                        item(contentType = ContentType.NO_ARTICLES) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "No articles found",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.SemiBold,
                                color = DataNotFoundColor
                            )
                        }
                    } else {
                        items(
                            items = state.articles,
                            contentType = { ContentType.ARTICLES }) { article ->
                            ArticleCard(article) {
                                // TODO: Handle article click
                                Toast.makeText(
                                    context,
                                    "Article clicked: ${article.title}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }

            item(contentType = ContentType.RECENT_ANNOUNCEMENTS) {
                SectionTitle(
                    title = "Recent Announcements",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 12.dp),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                )
                AnnouncementsCardsRow(
                    announcements = state.announcements,
                    createdAtColor = AnnouncementsFirstSectionBackgroundColor,
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

            item(contentType = ContentType.SCHEDULED_APPOINTMENTS_TABLE) {
                SectionTitle(
                    title = "Upcoming Appointments",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start
                )
                Table(
                    modifier = Modifier.heightIn(max = 300.dp),
                    headerCells = viewModel.appointmentsTableHeaderCellsData,
                    tableRows = state.appointmentsTableRowsData
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen {}
}
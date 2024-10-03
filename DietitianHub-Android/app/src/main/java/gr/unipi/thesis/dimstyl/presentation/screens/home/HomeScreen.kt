package gr.unipi.thesis.dimstyl.presentation.screens.home

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import gr.unipi.thesis.dimstyl.App
import gr.unipi.thesis.dimstyl.presentation.components.SectionTitle
import gr.unipi.thesis.dimstyl.presentation.components.cards.AnnouncementsCardsRow
import gr.unipi.thesis.dimstyl.presentation.components.cards.ArticleCard
import gr.unipi.thesis.dimstyl.presentation.components.circularProgressIndicators.ScreenCircularProgressIndicator
import gr.unipi.thesis.dimstyl.presentation.components.dialogs.CancelAppointmentAlertDialog
import gr.unipi.thesis.dimstyl.presentation.components.table.Table
import gr.unipi.thesis.dimstyl.presentation.screens.home.components.HorizontalDivider
import gr.unipi.thesis.dimstyl.presentation.theme.AnnouncementsFirstSectionBackgroundColor
import gr.unipi.thesis.dimstyl.presentation.theme.DataNotFoundColor
import gr.unipi.thesis.dimstyl.presentation.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.presentation.utils.ContentType
import gr.unipi.thesis.dimstyl.presentation.utils.LoginStatus
import gr.unipi.thesis.dimstyl.presentation.utils.viewModelFactory

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel<HomeViewModel>(
        factory = viewModelFactory {
            HomeViewModel(
                App.appModule.fetchHomeDataUseCase,
                App.appModule.cancelAppointmentUseCase
            )
        }
    ),
    loginStatus: LoginStatus,
    onNavigateToLoginScreen: () -> Unit,
    onSnackbarShow: (String, Boolean) -> Unit,
    onShowWebView: (String, Boolean) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (loginStatus == LoginStatus.LOGGED_OUT) {
        onNavigateToLoginScreen()
    } else if (state.isLoading) {
        ScreenCircularProgressIndicator()
        LaunchedEffect(Unit) {
            viewModel.fetchHomeData(
                onFetchHomeDataResult = { message, shortDuration ->
                    onSnackbarShow(message, shortDuration)
                }
            )
        }
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
                        text = "Welcome, ${state.fullName} !",
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
                                text = "Articles not found",
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
                                val endpoint = "articles/${article.id}/preview"
                                onShowWebView(endpoint, true)
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
                        val endpoint = "announcements/${announcement.id}/preview"
                        onShowWebView(endpoint, true)
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

    if (state.showCancelAppointmentDialog) {
        CancelAppointmentAlertDialog(
            onConfirm = {
                viewModel.cancelAppointment(
                    onCancelAppointmentResult = { message, shortDuration ->
                        onSnackbarShow(message, shortDuration)
                    })
            },
            onDismiss = { viewModel.showCancelAppointmentDialog(false) }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        loginStatus = LoginStatus.LOGGED_IN,
        onNavigateToLoginScreen = {},
        onSnackbarShow = { _, _ -> },
        onShowWebView = { _, _ -> }
    )
}
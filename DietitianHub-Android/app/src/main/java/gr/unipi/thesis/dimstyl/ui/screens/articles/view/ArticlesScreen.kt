package gr.unipi.thesis.dimstyl.ui.screens.articles.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import gr.unipi.thesis.dimstyl.ui.components.Card
import gr.unipi.thesis.dimstyl.ui.helpers.ContentType
import gr.unipi.thesis.dimstyl.ui.theme.ArticleCreatedAtColor
import gr.unipi.thesis.dimstyl.ui.theme.ArticleTitleColor
import gr.unipi.thesis.dimstyl.ui.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.ui.theme.TopBarColor

@Composable
fun ArticlesScreen() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = articles, contentType = { ContentType.ARTICLES }) {
            Card(
                title = it.title,
                createdAt = it.createdAt,
                titleColor = ArticleTitleColor,
                createdAtColor = ArticleCreatedAtColor,
                border = BorderStroke(
                    width = 1.5.dp,
                    brush = Brush.linearGradient(listOf(TopBarColor, LeftBarColor))
                )
            )
        }
    }
}

// Start of temporary data TODO: Remove this and use real data when available
data class Article(val id: Int, val title: String, val content: String, val createdAt: String)

val articles = listOf(
    Article(
        1,
        "New recipe for a delicious cake",
        "This is the content of the article",
        "16 Sep 2024\n07:54 PM"
    ),
    Article(
        2,
        "How to make a perfect coffee",
        "This is the content of the article",
        "16 Sep 2024\n07:54 PM"
    ),
    Article(
        3,
        "The best places to visit in Greece",
        "This is the content of the article",
        "16 Sep 2024\n07:54 PM"
    ),
    Article(
        4,
        "The benefits of a healthy lifestyle",
        "This is the content of the article",
        "16 Sep 2024\n07:54 PM"
    ),
    Article(
        5,
        "The importance of a good night's sleep",
        "This is the content of the article",
        "16 Sep 2024\n07:54 PM"
    ),
    Article(
        6,
        "The best exercises for a healthy body",
        "This is the content of the article",
        "16 Sep 2024\n07:54 PM"
    ),
    Article(
        7,
        "The benefits of a healthy diet",
        "This is the content of the article",
        "16 Sep 2024\n07:54 PM"
    ),
    Article(
        8,
        "The importance of a good night's sleep",
        "This is the content of the article",
        "16 Sep 2024\n07:54 PM"
    ),
    Article(
        9,
        "The best exercises for a healthy body",
        "This is the content of the article",
        "16 Sep 2024\n07:54 PM"
    ),
    Article(
        10,
        "The benefits of a healthy diet",
        "This is the content of the article",
        "16 Sep 2024\n07:54 PM"
    ),
    Article(
        11,
        "The importance of a good healthy diet and daily exercise with a good night's sleep",
        "This is the content of the article",
        "16 Sep 2024\n07:54 PM"
    ),
)
// End of temporary data

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ArticlesScreenPreview() {
    ArticlesScreen()
}
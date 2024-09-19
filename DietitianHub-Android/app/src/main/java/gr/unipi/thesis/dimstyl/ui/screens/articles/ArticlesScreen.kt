package gr.unipi.thesis.dimstyl.ui.screens.articles

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

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ArticlesScreenPreview() {
    ArticlesScreen()
}
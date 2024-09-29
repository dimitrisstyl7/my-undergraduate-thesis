package gr.unipi.thesis.dimstyl.presentation.components.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import gr.unipi.thesis.dimstyl.data.models.Article
import gr.unipi.thesis.dimstyl.presentation.theme.ArticleCreatedAtColor
import gr.unipi.thesis.dimstyl.presentation.theme.ArticleTitleColor
import gr.unipi.thesis.dimstyl.presentation.theme.LeftBarColor
import gr.unipi.thesis.dimstyl.presentation.theme.TopBarColor

@Composable
fun ArticleCard(article: Article, onClick: () -> Unit) {
    Card(
        title = article.title,
        createdAt = article.createdAt,
        width = 150.dp,
        titleColor = ArticleTitleColor,
        createdAtColor = ArticleCreatedAtColor,
        border = BorderStroke(
            width = 1.5.dp,
            brush = Brush.linearGradient(listOf(TopBarColor, LeftBarColor))
        ),
        onClick = onClick
    )
}
package gr.unipi.thesis.dimstyl.presentation.screens.articles

import gr.unipi.thesis.dimstyl.data.models.Article

data class ArticlesState(val articles: List<Article> = emptyList(), val isLoading: Boolean = true)
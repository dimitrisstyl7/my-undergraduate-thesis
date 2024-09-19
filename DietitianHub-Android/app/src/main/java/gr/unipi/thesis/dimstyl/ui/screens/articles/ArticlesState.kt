package gr.unipi.thesis.dimstyl.ui.screens.articles

import gr.unipi.thesis.dimstyl.data.model.Article

data class ArticlesState(val articles: List<Article> = emptyList(), val isLoading: Boolean = false)
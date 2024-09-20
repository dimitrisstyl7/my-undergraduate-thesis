package gr.unipi.thesis.dimstyl.ui.screens.articles

import androidx.lifecycle.ViewModel
import gr.unipi.thesis.dimstyl.data.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArticlesViewModel : ViewModel() {

    private val _state = MutableStateFlow(ArticlesState())
    val state = _state.asStateFlow()

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        _state.value = ArticlesState(isLoading = true)

        // TODO: Fetch articles from the server
        val articles = listOf(
            Article(
                1,
                "New recipe for a delicious cake",
                "16 Sep 2024\n07:54 PM"
            ),
            Article(
                2,
                "How to make a perfect coffee",
                "16 Sep 2024\n07:54 PM"
            ),
            Article(
                3,
                "The best places to visit in Greece",
                "16 Sep 2024\n07:54 PM"
            ),
            Article(
                4,
                "The benefits of a healthy lifestyle",
                "16 Sep 2024\n07:54 PM"
            ),
            Article(
                5,
                "The importance of a good night's sleep",
                "16 Sep 2024\n07:54 PM"
            ),
            Article(
                6,
                "The best exercises for a healthy body",
                "16 Sep 2024\n07:54 PM"
            ),
            Article(
                7,
                "The benefits of a healthy diet",
                "16 Sep 2024\n07:54 PM"
            ),
            Article(
                8,
                "The importance of a good night's sleep",
                "16 Sep 2024\n07:54 PM"
            ),
            Article(
                9,
                "The best exercises for a healthy body",
                "16 Sep 2024\n07:54 PM"
            ),
            Article(
                10,
                "The benefits of a healthy diet",
                "16 Sep 2024\n07:54 PM"
            ),
            Article(
                11,
                "The importance of a good healthy diet and daily exercise with a good night's sleep",
                "16 Sep 2024\n07:54 PM"
            ),
        )

        _state.value = ArticlesState(articles = articles, isLoading = false)
    }

}
package gr.unipi.thesis.dimstyl.domain.repositories

import gr.unipi.thesis.dimstyl.data.models.Article

interface ArticleRepository {

    suspend fun fetchArticles(): Result<List<Article>>

}
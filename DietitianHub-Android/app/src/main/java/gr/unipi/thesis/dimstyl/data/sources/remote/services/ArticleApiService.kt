package gr.unipi.thesis.dimstyl.data.sources.remote.services

import gr.unipi.thesis.dimstyl.data.models.Article
import retrofit2.Response
import retrofit2.http.GET

interface ArticleApiService {

    @GET("articles")
    suspend fun fetchArticles(): Response<List<Article>>

}
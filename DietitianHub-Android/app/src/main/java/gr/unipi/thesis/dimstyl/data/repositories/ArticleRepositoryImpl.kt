package gr.unipi.thesis.dimstyl.data.repositories

import android.util.Log
import gr.unipi.thesis.dimstyl.data.models.Article
import gr.unipi.thesis.dimstyl.data.sources.remote.services.ArticleApiService
import gr.unipi.thesis.dimstyl.domain.repositories.ArticleRepository
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.FETCH_ARTICLES_ERROR_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ArticleRepositoryImpl(private val articleApiService: ArticleApiService) : ArticleRepository {

    companion object {
        private const val TAG = "ArticleRepositoryImpl"
    }

    override suspend fun fetchArticles(): Result<List<Article>> {
        return withContext(Dispatchers.IO) {
            val response: Response<List<Article>>

            try {
                response = articleApiService.fetchArticles()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch articles", e)
                return@withContext Result.failure(Exception(FETCH_ARTICLES_ERROR_MESSAGE))
            }

            if (response.isSuccessful) {
                val body: List<Article>? = response.body()

                when {
                    body == null -> {
                        Log.e(TAG, "Failed to fetch articles: Response body is null")
                        return@withContext Result.failure(Exception(FETCH_ARTICLES_ERROR_MESSAGE))
                    }

                    else -> return@withContext Result.success(body)
                }
            } else {
                Log.e(TAG, "Failed to fetch articles: ${response.errorBody()?.string()}")
                return@withContext Result.failure(Exception(FETCH_ARTICLES_ERROR_MESSAGE))
            }
        }
    }

}
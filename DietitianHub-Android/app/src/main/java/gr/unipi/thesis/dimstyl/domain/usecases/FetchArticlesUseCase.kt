package gr.unipi.thesis.dimstyl.domain.usecases

import gr.unipi.thesis.dimstyl.domain.repositories.ArticleRepository

class FetchArticlesUseCase(private val articleRepository: ArticleRepository) {

    suspend operator fun invoke() = articleRepository.fetchArticles()

}
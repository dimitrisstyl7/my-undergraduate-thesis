package gr.unipi.thesis.dimstyl.presentation.screens.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.unipi.thesis.dimstyl.domain.usecases.FetchArticlesUseCase
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.FETCH_ARTICLES_ERROR_MESSAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArticlesViewModel(private val fetchArticlesUseCase: FetchArticlesUseCase) : ViewModel() {

    private val _state = MutableStateFlow(ArticlesState())
    val state = _state.asStateFlow()

    fun fetchArticles(onFetchArticlesResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val result = fetchArticlesUseCase()
            val articles = result.getOrNull()

            if (result.isSuccess && articles != null) {
                _state.value = _state.value.copy(articles = articles, isLoading = false)
            } else {
                val errorMessage =
                    result.exceptionOrNull()?.message ?: FETCH_ARTICLES_ERROR_MESSAGE
                onFetchArticlesResult(errorMessage, false)
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

}
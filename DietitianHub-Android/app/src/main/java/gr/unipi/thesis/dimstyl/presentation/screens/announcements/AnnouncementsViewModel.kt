package gr.unipi.thesis.dimstyl.presentation.screens.announcements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gr.unipi.thesis.dimstyl.domain.usecases.FetchAnnouncementsUseCase
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.FETCH_ANNOUNCEMENTS_ERROR_MESSAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnnouncementsViewModel(private val fetchAnnouncementsUseCase: FetchAnnouncementsUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow(AnnouncementsState())
    val state = _state.asStateFlow()

    fun fetchAnnouncements(onFetchAnnouncementsResult: (String, Boolean) -> Unit) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val result = fetchAnnouncementsUseCase()
            val announcementSections = result.getOrNull()

            if (result.isSuccess && announcementSections != null) {
                _state.value =
                    _state.value.copy(
                        announcementSections = announcementSections,
                        isLoading = false
                    )
            } else {
                val errorMessage =
                    result.exceptionOrNull()?.message ?: FETCH_ANNOUNCEMENTS_ERROR_MESSAGE
                onFetchAnnouncementsResult(errorMessage, false)
                _state.value = _state.value.copy(isLoading = false)
            }

        }
    }

}
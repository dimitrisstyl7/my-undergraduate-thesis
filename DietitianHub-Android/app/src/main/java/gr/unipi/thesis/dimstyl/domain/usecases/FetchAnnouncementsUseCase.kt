package gr.unipi.thesis.dimstyl.domain.usecases

import gr.unipi.thesis.dimstyl.domain.repositories.AnnouncementRepository
import gr.unipi.thesis.dimstyl.presentation.models.AnnouncementSectionWithColor
import gr.unipi.thesis.dimstyl.presentation.theme.AnnouncementsFirstSectionBackgroundColor
import gr.unipi.thesis.dimstyl.presentation.theme.AnnouncementsSecondSectionBackgroundColor
import gr.unipi.thesis.dimstyl.presentation.theme.AnnouncementsThirdSectionBackgroundColor
import gr.unipi.thesis.dimstyl.utils.Constants.ErrorMessages.FETCH_APPOINTMENTS_ERROR_MESSAGE

class FetchAnnouncementsUseCase(private val announcementRepository: AnnouncementRepository) {

    suspend operator fun invoke(): Result<List<AnnouncementSectionWithColor>> {
        val result = announcementRepository.fetchAnnouncements()
        val announcementSections = result.getOrNull()

        return if (result.isSuccess && announcementSections != null) {
            val announcementSectionsWithColor = mutableListOf<AnnouncementSectionWithColor>()

            for (index in announcementSections.indices) {
                val color = when (index) {
                    0 -> AnnouncementsFirstSectionBackgroundColor
                    1 -> AnnouncementsSecondSectionBackgroundColor
                    else -> AnnouncementsThirdSectionBackgroundColor
                }
                announcementSectionsWithColor.add(
                    announcementSections[index].toAnnouncementSectionsWithColor(color)
                )
            }
            Result.success(announcementSectionsWithColor)
        } else {
            val errorMessage =
                result.exceptionOrNull()?.message ?: FETCH_APPOINTMENTS_ERROR_MESSAGE
            Result.failure(Exception(errorMessage))
        }
    }

}
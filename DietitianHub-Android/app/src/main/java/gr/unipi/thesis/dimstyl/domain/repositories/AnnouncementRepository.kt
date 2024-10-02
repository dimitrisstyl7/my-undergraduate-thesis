package gr.unipi.thesis.dimstyl.domain.repositories

import gr.unipi.thesis.dimstyl.domain.models.AnnouncementSection

interface AnnouncementRepository {

    suspend fun fetchAnnouncements(): Result<List<AnnouncementSection>>

}
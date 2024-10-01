package gr.unipi.thesis.dimstyl.data.models

data class HomeResponse(
    val fullName: String,
    val articles: List<Article>,
    val announcements: List<Announcement>,
    val appointments: List<Appointment>
)
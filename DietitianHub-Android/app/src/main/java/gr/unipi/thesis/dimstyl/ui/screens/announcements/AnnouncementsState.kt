package gr.unipi.thesis.dimstyl.ui.screens.announcements

import gr.unipi.thesis.dimstyl.data.model.Section

data class AnnouncementsState(val sections: List<Section> = emptyList(), val isLoading: Boolean = false)
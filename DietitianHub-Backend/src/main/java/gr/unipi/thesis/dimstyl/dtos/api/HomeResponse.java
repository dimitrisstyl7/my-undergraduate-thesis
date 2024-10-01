package gr.unipi.thesis.dimstyl.dtos.api;

import lombok.Builder;

import java.util.List;

@Builder
public record HomeResponse(String fullName,
                           List<ApiArticleDto> articles,
                           List<ApiAnnouncementDto> announcements,
                           List<ApiAppointmentDto> appointments) {
}

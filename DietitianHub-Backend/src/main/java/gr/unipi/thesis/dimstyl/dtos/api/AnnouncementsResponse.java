package gr.unipi.thesis.dimstyl.dtos.api;

import lombok.Builder;

import java.util.List;

@Builder
public record AnnouncementsResponse(List<ApiAnnouncementDto> announcementsForToday,
                                    List<ApiAnnouncementDto> announcementsForYesterday,
                                    List<ApiAnnouncementDto> earlierAnnouncements) {
}

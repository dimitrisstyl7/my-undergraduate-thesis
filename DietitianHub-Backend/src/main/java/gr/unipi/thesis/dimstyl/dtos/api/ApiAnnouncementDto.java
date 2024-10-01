package gr.unipi.thesis.dimstyl.dtos.api;

import lombok.Builder;

@Builder
public record ApiAnnouncementDto(int id, String title, String createdAt) {
}

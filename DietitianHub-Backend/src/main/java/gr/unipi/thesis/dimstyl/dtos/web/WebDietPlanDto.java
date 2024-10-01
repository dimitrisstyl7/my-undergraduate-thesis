package gr.unipi.thesis.dimstyl.dtos.web;

import lombok.Builder;

@Builder
public record WebDietPlanDto(int id, String name, String createdOn) {
}

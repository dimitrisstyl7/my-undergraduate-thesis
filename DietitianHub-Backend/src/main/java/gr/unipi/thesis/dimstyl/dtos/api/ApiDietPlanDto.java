package gr.unipi.thesis.dimstyl.dtos.api;

import lombok.Builder;

@Builder
public record ApiDietPlanDto(int id, String createdOn) {
}
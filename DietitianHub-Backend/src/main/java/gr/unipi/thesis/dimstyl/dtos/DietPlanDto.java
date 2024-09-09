package gr.unipi.thesis.dimstyl.dtos;

import lombok.Builder;

@Builder
public record DietPlanDto(int id, String name, String createdOn) {
}

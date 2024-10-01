package gr.unipi.thesis.dimstyl.dtos.web;

import lombok.Builder;

@Builder
public record WebTagDto(int id, int categoryId, String name) {
}

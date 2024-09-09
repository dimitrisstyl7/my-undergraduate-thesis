package gr.unipi.thesis.dimstyl.dtos;

import lombok.Builder;

@Builder
public record TagDto(int id, int categoryId, String name) {
}

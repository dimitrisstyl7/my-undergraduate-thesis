package gr.unipi.thesis.dimstyl.dtos;

import lombok.Builder;

import java.util.List;

@Builder
public record TagCategoryDto(String name, List<TagDto> tags) {
}

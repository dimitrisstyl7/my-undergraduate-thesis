package gr.unipi.thesis.dimstyl.dtos.web;

import lombok.Builder;

import java.util.List;

@Builder
public record WebTagCategoryDto(String name, List<WebTagDto> tags) {
}

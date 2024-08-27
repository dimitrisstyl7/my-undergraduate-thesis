package com.dimstyl.dietitianhub.dtos;

import lombok.Builder;

import java.util.List;

@Builder
public record TagCategoryDto(String name, List<TagDto> tags) {
}

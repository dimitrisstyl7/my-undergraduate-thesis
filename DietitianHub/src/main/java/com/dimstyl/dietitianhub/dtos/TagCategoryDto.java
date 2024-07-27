package com.dimstyl.dietitianhub.dtos;

import java.util.List;

public record TagCategoryDto(String name, List<TagDto> tags) {
}

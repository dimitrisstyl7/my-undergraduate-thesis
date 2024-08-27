package com.dimstyl.dietitianhub.dtos;

import lombok.Builder;

@Builder
public record TagDto(int id, int categoryId, String name) {
}

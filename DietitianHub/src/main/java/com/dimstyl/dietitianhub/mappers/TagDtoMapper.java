package com.dimstyl.dietitianhub.mappers;

import com.dimstyl.dietitianhub.dtos.TagDto;
import com.dimstyl.dietitianhub.entities.Tag;
import com.dimstyl.dietitianhub.entities.TagCategory;

public class TagDtoMapper {
    public static Tag mapToTag(TagDto tagDto, TagCategory category) {
        return Tag.builder()
                .id(tagDto.getId())
                .category(category)
                .name(tagDto.getName())
                .build();
    }
}

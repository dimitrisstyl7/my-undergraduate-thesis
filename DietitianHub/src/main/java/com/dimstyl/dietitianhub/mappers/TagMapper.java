package com.dimstyl.dietitianhub.mappers;

import com.dimstyl.dietitianhub.dtos.TagDto;
import com.dimstyl.dietitianhub.entities.Tag;

public class TagMapper {
    public static TagDto mapToTagDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .categoryId(tag.getCategory().getId())
                .name(tag.getName())
                .build();
    }
}

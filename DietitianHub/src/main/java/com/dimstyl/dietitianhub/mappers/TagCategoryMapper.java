package com.dimstyl.dietitianhub.mappers;

import com.dimstyl.dietitianhub.dtos.TagDto;
import com.dimstyl.dietitianhub.dtos.TagCategoryDto;
import com.dimstyl.dietitianhub.entities.TagCategory;

import java.util.List;

public class TagCategoryMapper {

    public static TagCategoryDto mapToTagCategoryDto(TagCategory tagCategory) {
        List<TagDto> tags = tagCategory.getTags().stream()
                .map(TagMapper::mapToTagDto)
                .toList();

        return TagCategoryDto.builder()
                .name(tagCategory.getName())
                .tags(tags)
                .build();
    }

}

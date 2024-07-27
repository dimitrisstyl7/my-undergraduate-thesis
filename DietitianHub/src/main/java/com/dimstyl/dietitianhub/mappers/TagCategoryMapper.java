package com.dimstyl.dietitianhub.mappers;

import com.dimstyl.dietitianhub.dtos.TagCategoryDto;
import com.dimstyl.dietitianhub.dtos.TagDto;
import com.dimstyl.dietitianhub.entities.TagCategory;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public final class TagCategoryMapper {

    public static TagCategoryDto mapToTagCategoryDto(TagCategory tagCategory) {
        List<TagDto> tags = tagCategory.getTags().stream()
                .map(TagMapper::mapToTagDto)
                .toList();

        return new TagCategoryDto(tagCategory.getName(), tags);
    }

}

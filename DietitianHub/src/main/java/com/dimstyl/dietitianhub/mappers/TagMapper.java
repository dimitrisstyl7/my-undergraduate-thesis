package com.dimstyl.dietitianhub.mappers;

import com.dimstyl.dietitianhub.dtos.TagDto;
import com.dimstyl.dietitianhub.entities.Tag;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class TagMapper {

    public static TagDto mapToTagDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getCategory().getId(), tag.getName());
    }

}

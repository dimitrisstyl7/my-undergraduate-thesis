package com.dimstyl.dietitianhub.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class TagCategoryDto {

    private String name;
    private List<TagDto> tags;

}

package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.TagCategoryDto;
import com.dimstyl.dietitianhub.entities.TagCategory;

import java.util.List;

public interface TagCategoryService {
    List<TagCategoryDto> getAllTagCategoriesAndTags();

    TagCategory findById(Integer id);
}

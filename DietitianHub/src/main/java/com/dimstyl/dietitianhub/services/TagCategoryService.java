package com.dimstyl.dietitianhub.services;

import com.dimstyl.dietitianhub.dtos.TagCategoryDto;

import java.util.List;

public interface TagCategoryService {
    List<TagCategoryDto> getAllTagCategoriesAndTags();
}

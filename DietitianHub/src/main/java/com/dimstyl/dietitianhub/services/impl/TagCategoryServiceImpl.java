package com.dimstyl.dietitianhub.services.impl;

import com.dimstyl.dietitianhub.dtos.TagCategoryDto;
import com.dimstyl.dietitianhub.entities.TagCategory;
import com.dimstyl.dietitianhub.mappers.TagCategoryMapper;
import com.dimstyl.dietitianhub.repositories.TagCategoryRepository;
import com.dimstyl.dietitianhub.services.TagCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagCategoryServiceImpl implements TagCategoryService {
    private final TagCategoryRepository tagCategoryRepository;

    @Override
    public List<TagCategoryDto> getAllTagCategoriesAndTags() {
        List<TagCategory> tagCategories = tagCategoryRepository.getAllTagCategoriesAndTags();
        return tagCategories.stream()
                .map(TagCategoryMapper::mapToTagCategoryDto)
                .toList();
    }

    @Override
    public TagCategory findById(Integer id) {
        // Todo: Throw a custom exception if the tag category is not found and handle it in the custom exception handler.
        return tagCategoryRepository.findById(id).orElseThrow();
    }
}

package gr.unipi.thesis.dimstyl.services.impl;

import gr.unipi.thesis.dimstyl.dtos.web.WebTagCategoryDto;
import gr.unipi.thesis.dimstyl.entities.TagCategory;
import gr.unipi.thesis.dimstyl.repositories.TagCategoryRepository;
import gr.unipi.thesis.dimstyl.services.TagCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagCategoryServiceImpl implements TagCategoryService {

    private final TagCategoryRepository tagCategoryRepository;

    @Override
    public List<WebTagCategoryDto> getAllTagCategoriesAndTags() {
        List<TagCategory> tagCategories = tagCategoryRepository.getAllTagCategoriesAndTags();
        return tagCategories.stream()
                .map(TagCategory::toWebDto)
                .toList();
    }

}

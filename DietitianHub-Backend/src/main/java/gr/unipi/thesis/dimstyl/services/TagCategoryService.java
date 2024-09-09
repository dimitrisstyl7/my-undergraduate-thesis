package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.TagCategoryDto;

import java.util.List;

public interface TagCategoryService {

    List<TagCategoryDto> getAllTagCategoriesAndTags();

}

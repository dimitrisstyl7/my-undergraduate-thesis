package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.web.WebTagCategoryDto;

import java.util.List;

public interface TagCategoryService {

    List<WebTagCategoryDto> getAllTagCategoriesAndTags();

}

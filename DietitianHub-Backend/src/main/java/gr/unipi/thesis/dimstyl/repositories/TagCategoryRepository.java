package gr.unipi.thesis.dimstyl.repositories;

import gr.unipi.thesis.dimstyl.entities.TagCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagCategoryRepository extends JpaRepository<TagCategory, Integer> {

    @Query("SELECT tc FROM TagCategory tc JOIN FETCH tc.tags")
    List<TagCategory> getAllTagCategoriesAndTags();

}

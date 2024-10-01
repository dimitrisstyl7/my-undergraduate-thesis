package gr.unipi.thesis.dimstyl.repositories;

import gr.unipi.thesis.dimstyl.entities.Article;
import gr.unipi.thesis.dimstyl.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    @Query("SELECT a FROM Article a JOIN FETCH a.tags ts WHERE ts IN :tags ORDER BY a.createdAt DESC")
    List<Article> findFirst10ByTagsOrderByCreatedAtDesc(List<Tag> tags);

    boolean existsByTitle(String title);

}

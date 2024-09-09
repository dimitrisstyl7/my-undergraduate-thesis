package gr.unipi.thesis.dimstyl.repositories;

import gr.unipi.thesis.dimstyl.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    boolean existsByTitle(String title);

}

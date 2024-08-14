package com.dimstyl.dietitianhub.repositories;

import com.dimstyl.dietitianhub.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    boolean existsByTitle(String title);

}

package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.ArticleDto;

import java.util.List;

public interface ArticleService {

    List<ArticleDto> getAllArticles();

    ArticleDto getArticle(int id);

    void createArticle(ArticleDto articleDto);

    void updateArticle(int id, ArticleDto articleDto);

    void deleteArticle(int id);

    boolean existsByTitle(String title);

}

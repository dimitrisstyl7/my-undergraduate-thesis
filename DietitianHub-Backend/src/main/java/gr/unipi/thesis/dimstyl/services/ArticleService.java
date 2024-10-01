package gr.unipi.thesis.dimstyl.services;

import gr.unipi.thesis.dimstyl.dtos.web.WebArticleDto;

import java.util.List;

public interface ArticleService {

    List<WebArticleDto> getAllArticles();


    WebArticleDto getArticle(int id);

    void createArticle(WebArticleDto webArticleDto);

    void updateArticle(int id, WebArticleDto webArticleDto);

    void deleteArticle(int id);

    boolean existsByTitle(String title);

}

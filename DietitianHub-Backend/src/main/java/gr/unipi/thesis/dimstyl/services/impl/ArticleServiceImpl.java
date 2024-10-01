package gr.unipi.thesis.dimstyl.services.impl;

import gr.unipi.thesis.dimstyl.dtos.api.ApiArticleDto;
import gr.unipi.thesis.dimstyl.dtos.web.WebArticleDto;
import gr.unipi.thesis.dimstyl.entities.Article;
import gr.unipi.thesis.dimstyl.entities.Tag;
import gr.unipi.thesis.dimstyl.exceptions.article.ArticleNotFoundException;
import gr.unipi.thesis.dimstyl.repositories.ArticleRepository;
import gr.unipi.thesis.dimstyl.services.ArticleService;
import gr.unipi.thesis.dimstyl.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TagService tagService;

    @Override
    public List<WebArticleDto> getAllArticles() {
        return articleRepository.findAll(Sort.by(Sort.Order.desc("createdAt"))).stream()
                .map(Article::toWebDto)
                .toList();
    }

    @Override
    public List<ApiArticleDto> getLatest10ArticlesByTags(List<Tag> tags) {
        return articleRepository.findFirst10ByTagsOrderByCreatedAtDesc(tags).stream()
                .map(Article::toApiDto)
                .toList();
    }

    @Override
    public WebArticleDto getArticle(int id) {
        return articleRepository.findById(id)
                .map(Article::toWebDto)
                .orElseThrow(() -> new ArticleNotFoundException("Article with id %d not found".formatted(id)));
    }

    @Override
    @Transactional
    public void createArticle(WebArticleDto webArticleDto) {
        List<Tag> tags = tagService.getTags(webArticleDto.tagIds());
        Article article = webArticleDto.toArticle(tags);
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void updateArticle(int id, WebArticleDto webArticleDto) {
        List<Tag> tags = tagService.getTags(webArticleDto.tagIds());
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article with id %d not found".formatted(id)));
        article.setTitle(webArticleDto.title());
        article.setContent(webArticleDto.content());
        article.setTags(tags);
    }

    @Override
    @Transactional
    public void deleteArticle(int id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article with id %d not found".formatted(id)));
        articleRepository.delete(article);
    }

    @Override
    public boolean existsByTitle(String title) {
        return articleRepository.existsByTitle(title);
    }

}

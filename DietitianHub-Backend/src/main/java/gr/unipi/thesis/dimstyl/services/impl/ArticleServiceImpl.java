package gr.unipi.thesis.dimstyl.services.impl;

import gr.unipi.thesis.dimstyl.dtos.ArticleDto;
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
    public List<ArticleDto> getAllArticles() {
        return articleRepository.findAll(Sort.by(Sort.Order.desc("createdAt"))).stream()
                .map(Article::toDto)
                .toList();
    }

    @Override
    public ArticleDto getArticle(int id) {
        return articleRepository.findById(id)
                .map(Article::toDto)
                .orElseThrow(() -> new ArticleNotFoundException("Article with id %d not found".formatted(id)));
    }

    @Override
    @Transactional
    public void createArticle(ArticleDto articleDto) {
        List<Tag> tags = tagService.getTags(articleDto.tagIds());
        Article article = articleDto.toArticle(tags);
        articleRepository.save(article);
    }

    @Override
    @Transactional
    public void updateArticle(int id, ArticleDto articleDto) {
        List<Tag> tags = tagService.getTags(articleDto.tagIds());
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article with id %d not found".formatted(id)));
        article.setTitle(articleDto.title());
        article.setContent(articleDto.content());
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
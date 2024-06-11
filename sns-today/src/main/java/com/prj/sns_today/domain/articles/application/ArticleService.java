package com.prj.sns_today.domain.articles.application;

import com.prj.sns_today.domain.articles.domain.Article;
import com.prj.sns_today.domain.articles.dto.request.PostArticleRequest;
import com.prj.sns_today.domain.articles.dto.response.ArticleResponse;
import com.prj.sns_today.domain.articles.repository.ArticleRepository;
import com.prj.sns_today.domain.like.repository.LikeRepository;
import com.prj.sns_today.domain.users.domain.User;
import com.prj.sns_today.domain.users.repository.UserRepository;
import com.prj.sns_today.global.exception.ApplicationException;
import com.prj.sns_today.global.exception.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;
  private final LikeRepository likeRepository;


  @Transactional
  public void postArticle(
      Authentication authentication, PostArticleRequest request) {
    User user = userRepository.findByUsername(authentication.getName())
        .orElseThrow(() -> new ApplicationException(
            ErrorCode.USER_NOT_FOUND));
    articleRepository.save(Article.of(user, request.getTitle(), request.getContent()));
  }


  @Transactional
  public void updateArticle(Long articleId, Authentication authentication,
      PostArticleRequest request) {
    User user = userRepository.findByUsername(authentication.getName())
        .orElseThrow(() -> new ApplicationException(
            ErrorCode.USER_NOT_FOUND));
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.ARTICLE_NOT_FOUND)
        );
    if (!article.getUser().equals(user)) {
      throw new ApplicationException(ErrorCode.INVALID_PERMISSION);
    }
    article.updateArticle(request.getTitle(), request.getContent());
  }


  @Transactional
  public void deleteArticle(Long articleId, Authentication authentication) {
    User user = userRepository.findByUsername(authentication.getName())
        .orElseThrow(() -> new ApplicationException(
            ErrorCode.USER_NOT_FOUND));
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.ARTICLE_NOT_FOUND)
        );
    if (!article.getUser().equals(user)) {
      throw new ApplicationException(ErrorCode.INVALID_PERMISSION);
    }
    articleRepository.delete(article);
  }

  // Todo :  need isLike
  @Transactional(readOnly = true)
  public ArticleResponse getArticleDetails(Long articleId) {
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.ARTICLE_NOT_FOUND));
    return new ArticleResponse(articleId, article.getUser().getUsername(), article.getTitle(),
        article.getContent(), false);
  }

  // Todo :  need isLike
  @Transactional(readOnly = true)
  public List<ArticleResponse> getArticles() {
    List<Article> all = articleRepository.findAll();

    return all.stream()
        .map(article -> new ArticleResponse(article.getId(), article.getUser().getUsername(),
            article.getTitle(), article.getContent(), false)).collect(Collectors.toList());
  }
}

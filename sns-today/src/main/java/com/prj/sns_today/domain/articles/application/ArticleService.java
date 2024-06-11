package com.prj.sns_today.domain.articles.application;

import com.prj.sns_today.domain.articles.domain.Article;
import com.prj.sns_today.domain.articles.dto.PostArticleRequest;
import com.prj.sns_today.domain.articles.repository.ArticleRepository;
import com.prj.sns_today.domain.users.domain.User;
import com.prj.sns_today.domain.users.repository.UserRepository;
import com.prj.sns_today.global.exception.ApplicationException;
import com.prj.sns_today.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;


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
}

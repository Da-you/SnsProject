package com.prj.sns_today.domain.articles.application;

import com.prj.sns_today.domain.articles.domain.Article;
import com.prj.sns_today.domain.articles.dto.request.PostArticleRequest;
import com.prj.sns_today.domain.articles.dto.response.ArticleDetailResponse;
import com.prj.sns_today.domain.articles.dto.response.ArticleResponse;
import com.prj.sns_today.domain.articles.repository.ArticleRepository;
import com.prj.sns_today.domain.comment.domain.Comment;
import com.prj.sns_today.domain.comment.dto.response.CommentResponse;
import com.prj.sns_today.domain.comment.repository.CommentRepository;
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
  private final CommentRepository commentRepository;


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

  @Transactional(readOnly = true)
  public ArticleDetailResponse getArticleDetails(Long articleId) {
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.ARTICLE_NOT_FOUND));
    List<Comment> comments = commentRepository.findAllByArticle(article);

    List<CommentResponse> res = comments.stream().map(
        comment -> new CommentResponse(comment.getId(), comment.getUser().getUsername(),
            comment.getContent())).collect(
        Collectors.toList());

    return new ArticleDetailResponse(articleId, article.getUser().getUsername(), article.getTitle(),
        article.getContent(), res);
  }

  // Todo :  need isLike ans user information
  @Transactional(readOnly = true)
  public List<ArticleResponse> getArticles(Authentication authentication) {
    User user = userRepository.findByUsername(authentication.getName())
        .orElseThrow(() -> new ApplicationException(
            ErrorCode.USER_NOT_FOUND));

    List<Article> all = articleRepository.findAll();

    return all.stream()
        .map(article -> new ArticleResponse(article.getId(), article.getUser().getUsername(),
            article.getTitle(), article.getContent(),
            likeRepository.existsByUserAndArticle(user, article)))
        .collect(Collectors.toList());
  }
}

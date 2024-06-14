package com.prj.sns_today.domain.like.application;

import com.prj.sns_today.domain.articles.domain.Article;
import com.prj.sns_today.domain.articles.repository.ArticleRepository;
import com.prj.sns_today.domain.like.domain.Like;
import com.prj.sns_today.domain.like.repository.LikeRepository;
import com.prj.sns_today.domain.users.domain.User;
import com.prj.sns_today.domain.users.repository.UserRepository;
import com.prj.sns_today.global.exception.ApplicationException;
import com.prj.sns_today.global.exception.ErrorCode;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;
  private final LikeRepository likeRepository;

  @Transactional
  public void executeLike(Long articleId, Long currentId) {
    User user = userRepository.findById(currentId).orElseThrow(()
        -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.ARTICLE_NOT_FOUND));
    Like like = likeRepository.findByUserAndArticle(user, article);
    if (like != null) {
      throw new ApplicationException(ErrorCode.ARTICLE_LIKE_EXCEPTION);
    }
    likeRepository.save(Like.executeLike(article, user));
  }

  @Transactional
  public void unExecute(Long articleId, Long currentId) {
    User user = userRepository.findById(currentId).orElseThrow(()
        -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.ARTICLE_NOT_FOUND));
    Like like = likeRepository.findByUserAndArticle(user, article);
    if (like == null) {
      throw new ApplicationException(ErrorCode.ARTICLE_LIKE_EXCEPTION);
    } else {
      likeRepository.delete(like);
    }
  }
}
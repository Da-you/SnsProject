package com.prj.sns_today.domain.articles.application;

import com.prj.sns_today.domain.articles.domain.Article;
import com.prj.sns_today.domain.articles.dto.PostArticleRequest;
import com.prj.sns_today.domain.articles.repository.ArticleRepository;
import com.prj.sns_today.domain.users.domain.User;
import com.prj.sns_today.domain.users.repository.UserRepository;
import com.prj.sns_today.global.exception.ApplicationException;
import com.prj.sns_today.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;


  @Transactional
  public void postArticle(String username, PostArticleRequest request) {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new ApplicationException(
        ErrorCode.USER_NOT_FOUND));

    articleRepository.save(Article.of(user, request.getTitle(), request.getTitle()));

  }
}

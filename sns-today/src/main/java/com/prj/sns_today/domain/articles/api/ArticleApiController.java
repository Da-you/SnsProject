package com.prj.sns_today.domain.articles.api;

import com.prj.sns_today.domain.articles.application.ArticleService;
import com.prj.sns_today.domain.articles.dto.PostArticleRequest;
import com.prj.sns_today.domain.users.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleApiController {

  private final ArticleService articleService;
  @PostMapping("/post")
  public void postArticle(String username, @RequestBody PostArticleRequest request){
    articleService.postArticle(username, request);

  }
}

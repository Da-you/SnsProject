package com.prj.sns_today.domain.articles.api;

import com.prj.sns_today.domain.articles.application.ArticleService;
import com.prj.sns_today.domain.articles.dto.request.PostArticleRequest;
import com.prj.sns_today.domain.articles.dto.response.ArticleResponse;
import com.prj.sns_today.global.response.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleApiController {

  private final ArticleService articleService;

  @PostMapping
  public void postArticle(Authentication authentication, @RequestBody PostArticleRequest request) {
    articleService.postArticle(authentication, request);
  }

  @PatchMapping("/{articleId}")
  public void updateArticle(@PathVariable Long articleId, Authentication authentication,
      PostArticleRequest request) {
    articleService.updateArticle(articleId, authentication, request);
  }

  @DeleteMapping("/{articleId}")
  public void deleteArticle(@PathVariable Long articleId, Authentication authentication) {
    articleService.deleteArticle(articleId, authentication);
  }

  @GetMapping("{articleId}")
  public ApiResponse<ArticleResponse> getArticleDetails(@PathVariable Long articleId) {
    return ApiResponse.success(articleService.getArticleDetails(articleId));
  }

  @GetMapping
  public ApiResponse<List<ArticleResponse>> getArticles() {
    return ApiResponse.success(articleService.getArticles());
  }
}

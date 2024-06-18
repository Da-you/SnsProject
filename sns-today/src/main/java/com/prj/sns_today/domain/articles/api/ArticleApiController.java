package com.prj.sns_today.domain.articles.api;

import com.prj.sns_today.domain.articles.application.ArticleService;
import com.prj.sns_today.domain.articles.dto.request.PostArticleRequest;
import com.prj.sns_today.domain.articles.dto.response.ArticleDetailResponse;
import com.prj.sns_today.domain.articles.dto.response.ArticleResponse;
import com.prj.sns_today.domain.like.application.LikeService;
import com.prj.sns_today.domain.like.domain.Like;
import com.prj.sns_today.global.annotation.CurrentUser;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleApiController {

  private final ArticleService articleService;
  private final LikeService likeService;

//  @PostMapping
//  public void postArticle(@CurrentUser Long currentId, @RequestBody PostArticleRequest request) {
//    articleService.postArticle(currentId, request);
//  }
  @PostMapping
  public void postArticle(@CurrentUser Long currentId,
      @RequestPart PostArticleRequest request,
      @RequestPart MultipartFile[] files) {
    articleService.postArticle(currentId, request, files);
  }
  @PatchMapping("/{articleId}")
  public void updateArticle(@PathVariable Long articleId, @CurrentUser Long currentId,
      PostArticleRequest request) {
    articleService.updateArticle(articleId, currentId, request);
  }

  @DeleteMapping("/{articleId}")
  public void deleteArticle(@PathVariable Long articleId, @CurrentUser Long currentId) {
    articleService.deleteArticle(articleId, currentId);
  }

  @GetMapping("{articleId}")
  public ApiResponse<ArticleDetailResponse> getArticleDetails(@PathVariable Long articleId) {
    return ApiResponse.success(articleService.getArticleDetails(articleId));
  }

  @GetMapping
  public ApiResponse<List<ArticleResponse>> getArticles(@CurrentUser Long currentId) {
    return ApiResponse.success(articleService.getArticles(currentId));
  }

  @PostMapping("/{articleId}/like")
  public void executeLike(@PathVariable Long articleId, @CurrentUser Long currentId) {
    likeService.executeLike(articleId, currentId);
  }

  @DeleteMapping("/{articleId}/unLike")
  public void unExecuteLike(@PathVariable Long articleId, @CurrentUser Long currentId) {
    likeService.unExecute(articleId, currentId);
  }
}

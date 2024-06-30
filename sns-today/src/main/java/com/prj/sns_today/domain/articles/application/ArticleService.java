package com.prj.sns_today.domain.articles.application;

import com.prj.sns_today.domain.articles.domain.Article;
import com.prj.sns_today.domain.articles.dto.request.PostArticleRequest;
import com.prj.sns_today.domain.articles.dto.response.ArticleDetailResponse;
import com.prj.sns_today.domain.articles.dto.response.ArticleResponse;
import com.prj.sns_today.domain.articles.repository.ArticleRepository;
import com.prj.sns_today.domain.comment.domain.Comment;
import com.prj.sns_today.domain.comment.dto.response.CommentResponse;
import com.prj.sns_today.domain.comment.repository.CommentRepository;
import com.prj.sns_today.domain.image.domain.Image;
import com.prj.sns_today.domain.image.repository.ImageRepository;
import com.prj.sns_today.domain.like.repository.LikeRepository;
import com.prj.sns_today.domain.users.domain.User;
import com.prj.sns_today.domain.users.repository.UserRepository;
import com.prj.sns_today.global.application.S3UploadService;
import com.prj.sns_today.global.exception.ApplicationException;
import com.prj.sns_today.global.exception.ErrorCode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ArticleService {

  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;
  private final LikeRepository likeRepository;
  private final CommentRepository commentRepository;
  private final ImageRepository imageRepository;
  private final S3UploadService uploadService;


  @Transactional
  public void postArticle(
      Long currentId, PostArticleRequest request, MultipartFile[] files) {
    User user = userRepository.findById(currentId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    List<String> images = new ArrayList<>();
    try {
      images = uploadService.uploadArticleImage(files);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Article article = Article.of(user, request.getTitle(), request.getContent());
    articleRepository.save(article);
    List<Image> entities = new ArrayList<>();
    if (images != null) {
      for (int i = 0; i < images.size(); i++) {
        Image entity = Image.of(article, images.get(i));
        entities.add(entity);
      }
    }
    imageRepository.saveAll(entities);
  }


  @Transactional
  public void updateArticle(Long articleId, Long currentId,
      PostArticleRequest request, MultipartFile[] files) {
    User user = userRepository.findById(currentId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.ARTICLE_NOT_FOUND)
        );
    if (!article.getUser().equals(user)) {
      throw new ApplicationException(ErrorCode.INVALID_PERMISSION);
    }
    article.updateArticle(request.getTitle(), request.getContent());
    // Todo : update article images
  }


  @Transactional
  public void deleteArticle(Long articleId, Long currentId) {
    User user = userRepository.findById(currentId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
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

  @Transactional(readOnly = true)
  public List<ArticleResponse> getArticles(Long currentId) {
    User user = userRepository.findById(currentId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

    List<Article> all = articleRepository.findAll();

    return all.stream()
        .map(article -> new ArticleResponse(article.getId(), article.getUser().getUsername(),
            article.getTitle(), article.getContent(),
            likeRepository.existsByUserAndArticle(user, article)))
        .collect(Collectors.toList());
  }
}

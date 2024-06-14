package com.prj.sns_today.domain.comment.application;

import com.prj.sns_today.domain.articles.domain.Article;
import com.prj.sns_today.domain.articles.repository.ArticleRepository;
import com.prj.sns_today.domain.comment.domain.Comment;
import com.prj.sns_today.domain.comment.dto.request.PostCommentRequest;
import com.prj.sns_today.domain.comment.repository.CommentRepository;
import com.prj.sns_today.domain.users.domain.User;
import com.prj.sns_today.domain.users.repository.UserRepository;
import com.prj.sns_today.global.exception.ApplicationException;
import com.prj.sns_today.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final ArticleRepository articleRepository;

  @Transactional
  public void postComment(Long articleId, Long currentId,
      PostCommentRequest request) {
    User user = userRepository.findById(currentId).orElseThrow(()
        -> new ApplicationException(ErrorCode.USER_NOT_FOUND));
    Article article = articleRepository.findById(articleId)
        .orElseThrow(() -> new ApplicationException(ErrorCode.ARTICLE_NOT_FOUND)
        );
    commentRepository.save(Comment.of(article, user, request.getContent()));
  }

  @Transactional
  public void updateComment(Long commentId, Long currentId,
      PostCommentRequest request) {
    User user = userRepository.findById(currentId).orElseThrow(()
        -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

    Comment comment = commentRepository.findByIdAndUser(commentId, user)
        .orElseThrow(() -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));

    comment.updateComment(request.getContent());
  }


  @Transactional
  public void deleteComment(Long commentId, Long currentId) {
    User user = userRepository.findById(currentId).orElseThrow(()
        -> new ApplicationException(ErrorCode.USER_NOT_FOUND));

    Comment comment = commentRepository.findByIdAndUser(commentId, user)
        .orElseThrow(() -> new ApplicationException(ErrorCode.COMMENT_NOT_FOUND));

    commentRepository.delete(comment);
  }

}

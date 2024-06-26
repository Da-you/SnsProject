package com.prj.sns_today.domain.comment.api;

import com.prj.sns_today.domain.comment.application.CommentService;
import com.prj.sns_today.domain.comment.dto.request.PostCommentRequest;
import com.prj.sns_today.global.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentApiController {

  private final CommentService commentService;

  @PostMapping("/{articleId}")
  public void postComment(@PathVariable Long articleId,@CurrentUser Long currentId,
      @RequestBody PostCommentRequest request) {
    commentService.postComment(articleId, currentId, request);
  }

  @PatchMapping("/{articleId}")
  public void updateComment(@PathVariable Long articleId, @CurrentUser Long currentId,
      @RequestBody PostCommentRequest request) {
    commentService.updateComment(articleId, currentId, request);
  }

  @DeleteMapping("/{articleId}")
  public void updateComment(@PathVariable Long articleId, @CurrentUser Long currentId) {
    commentService.deleteComment(articleId, currentId);
  }

}

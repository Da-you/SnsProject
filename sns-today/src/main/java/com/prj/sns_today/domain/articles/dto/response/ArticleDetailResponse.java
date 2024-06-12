package com.prj.sns_today.domain.articles.dto.response;

import com.prj.sns_today.domain.comment.dto.response.CommentResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ArticleDetailResponse {

  private Long articleId;
  private String writer;
  private String title;
  private String content;
  private List<CommentResponse> comments;
}

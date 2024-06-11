package com.prj.sns_today.domain.articles.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponse {

  private Long articleId;
  private String writer;
  private String title;
  private String content;


}

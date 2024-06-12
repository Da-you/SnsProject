package com.prj.sns_today.domain.comment.domain;

import com.prj.sns_today.domain.articles.domain.Article;
import com.prj.sns_today.domain.users.domain.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "article_id")
  private Article article;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  private String content;


  public static Comment of(Article article, User user, String content) {
    Comment comment = new Comment();
    comment.article = article;
    comment.user = user;
    comment.content = content;
    return comment;
  }

  public void updateComment(String content) {
    this.content = content;
  }
}

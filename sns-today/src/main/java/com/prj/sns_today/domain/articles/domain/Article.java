package com.prj.sns_today.domain.articles.domain;

import com.prj.sns_today.domain.image.domain.Image;
import com.prj.sns_today.domain.users.domain.User;
import com.prj.sns_today.domain.users.model.BaseTime;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "article")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Article extends BaseTime {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "user_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  private String title;

  private String content;

  @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
  private List<Image> images = new LinkedList<>();

  public static Article of(User user, String title, String content) {
    Article article = new Article();
    article.user = user;
    article.title = title;
    article.content = content;
    return article;
  }

  public void updateArticle(String title, String content) {
    this.title = title;
    this.content = content;
  }
}

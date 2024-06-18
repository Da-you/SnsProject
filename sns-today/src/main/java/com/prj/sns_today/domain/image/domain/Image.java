package com.prj.sns_today.domain.image.domain;

import com.prj.sns_today.domain.articles.domain.Article;
import com.prj.sns_today.domain.users.model.BaseTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "article_id")
  private Article article;

  private String imagePath;


  public Image(Article article, String imagePath) {
    this.article = article;
    this.imagePath = imagePath;
  }

  public static Image of(Article article, String imagePath) {
    Image image = new Image();
    image.article = article;
    image.imagePath = imagePath;
    return image;
  }
}

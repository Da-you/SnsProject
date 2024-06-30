package com.prj.sns_today.domain.image.repository;

import com.prj.sns_today.domain.articles.domain.Article;
import com.prj.sns_today.domain.image.domain.Image;
import java.util.LinkedList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

  List<Image> findAllByArticle(Article article);

  void deleteAllByArticle(Article article);
}

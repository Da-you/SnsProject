package com.prj.sns_today.domain.articles.repository;

import com.prj.sns_today.domain.articles.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}

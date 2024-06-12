package com.prj.sns_today.domain.like.repository;

import com.prj.sns_today.domain.articles.domain.Article;
import com.prj.sns_today.domain.like.domain.Like;
import com.prj.sns_today.domain.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

  Like findByUserAndArticle(User user, Article article);

  boolean existsByUserAndArticle(User user, Article article);
}

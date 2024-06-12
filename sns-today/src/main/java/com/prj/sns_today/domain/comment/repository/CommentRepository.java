package com.prj.sns_today.domain.comment.repository;

import com.prj.sns_today.domain.articles.domain.Article;
import com.prj.sns_today.domain.comment.domain.Comment;
import com.prj.sns_today.domain.users.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  Optional<Comment> findByIdAndUser(Long commentId, User user);

  List<Comment> findAllByArticle(Article article);
}

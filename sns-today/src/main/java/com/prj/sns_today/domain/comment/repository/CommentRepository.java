package com.prj.sns_today.domain.comment.repository;

import com.prj.sns_today.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}

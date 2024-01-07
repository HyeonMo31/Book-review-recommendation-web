package com.web.bookservice.repository.jpa;

import com.web.bookservice.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaCommentRepository extends JpaRepository<Comment, Long> {
}

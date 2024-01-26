package com.web.bookservice.repository.jpa;

import com.web.bookservice.domain.Comment;
import com.web.bookservice.domain.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByDiscussion(Discussion discussion);
}

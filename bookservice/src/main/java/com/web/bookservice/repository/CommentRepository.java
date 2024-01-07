package com.web.bookservice.repository;

import com.web.bookservice.domain.Comment;
import com.web.bookservice.repository.jpa.SpringDataJpaCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final SpringDataJpaCommentRepository repository;

    public void save(Comment comment) {
        repository.save(comment);
    }

    public void deleteComment(Long commentIndex) {
        repository.deleteById(commentIndex);
    }

}

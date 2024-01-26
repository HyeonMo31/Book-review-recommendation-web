package com.web.bookservice.service;

import com.web.bookservice.domain.Comment;
import com.web.bookservice.domain.Discussion;
import com.web.bookservice.domain.Member;
import com.web.bookservice.repository.jpa.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;

    public void save(Discussion discussion, Member member, String text) {

        Comment comment = new Comment();

        comment.setMember(member);
        comment.setText(text);
        comment.setWriteDate(LocalDateTime.now());


        comment.setDiscussion(discussion);

        repository.save(comment);
    }

    public void deleteComment(Comment comment) {

        repository.delete(comment);
    }

    public List<Comment> findByDiscussoin(Discussion discussion) {
        return repository.findByDiscussion(discussion);
    }

    public Comment findByComment(Long id) {
        return repository.findById(id).get();
    }
}

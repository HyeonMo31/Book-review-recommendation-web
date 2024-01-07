package com.web.bookservice.service;

import com.web.bookservice.domain.Comment;
import com.web.bookservice.domain.Discussion;
import com.web.bookservice.domain.Member;
import com.web.bookservice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;

    public void save(Optional<Discussion> discussion, Member member, String text) {

        Comment comment = new Comment();

        comment.setMember(member);
        comment.setText(text);
        comment.setWriteDate(LocalDateTime.now());

        if(discussion.isPresent()) {
            comment.setDiscussion(discussion.get());
        }

        repository.save(comment);
    }

    public void deleteComment(Long commentIndex) {
        repository.deleteComment(commentIndex);
    }
}

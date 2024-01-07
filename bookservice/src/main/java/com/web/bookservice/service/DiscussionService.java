package com.web.bookservice.service;

import com.web.bookservice.domain.Discussion;
import com.web.bookservice.repository.DiscussionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscussionService {

    private final DiscussionRepository repository;

    public Long save(Discussion discussion) {
        return repository.save(discussion);
    }

    public Optional<Discussion> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteDiscussion(Long discussionIndex) {
        repository.deleteDiscussion(discussionIndex);
    }

    public void update(Long discussionIndex, String title, String text) {
        Optional<Discussion> findDiscussion = findById(discussionIndex);

        findDiscussion.get().setTitle(title);
        findDiscussion.get().setText(text);

        repository.save(findDiscussion.get());
    }

    public List<Discussion> findAll() {
        return repository.findAll();
    }
}

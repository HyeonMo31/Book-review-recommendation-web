package com.web.bookservice.repository;

import com.web.bookservice.domain.Discussion;
import com.web.bookservice.repository.jpa.SpringDataJpaDiscussionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DiscussionRepository {

    private final SpringDataJpaDiscussionRepository repository;

    public Long save(Discussion discussion) {

        return repository.save(discussion).getDiscussionIndex();
    }

    public Optional<Discussion> findById(Long id) {
        return repository.findById(id);
    }

    public void deleteDiscussion(Long discussionIndex) {
        repository.deleteById(discussionIndex);
    }

    public List<Discussion> findAll() {
        return repository.findAll();
    }

}

package com.web.bookservice.repository;

import com.web.bookservice.domain.Discussion;
import com.web.bookservice.repository.jpa.SpringDataJpaDiscussionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class DiscussionRepository {

    private final SpringDataJpaDiscussionRepository repository;

    public void save(Discussion discussion) {
        repository.save(discussion);
    }
}

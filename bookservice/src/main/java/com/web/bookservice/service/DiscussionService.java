package com.web.bookservice.service;

import com.web.bookservice.domain.Discussion;
import com.web.bookservice.repository.DiscussionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscussionService {

    private final DiscussionRepository repository;

    public void save(Discussion discussion) {
        repository.save(discussion);
    }
}

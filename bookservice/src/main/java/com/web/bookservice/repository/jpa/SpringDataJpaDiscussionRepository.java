package com.web.bookservice.repository.jpa;

import com.web.bookservice.domain.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaDiscussionRepository extends JpaRepository<Discussion, Long> {
}

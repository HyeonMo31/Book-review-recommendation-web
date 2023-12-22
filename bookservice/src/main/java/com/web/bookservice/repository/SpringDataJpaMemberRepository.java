package com.web.bookservice.repository;

import com.web.bookservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long> {

    boolean existsById(String id);
}

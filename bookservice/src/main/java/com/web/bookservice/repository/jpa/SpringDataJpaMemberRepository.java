package com.web.bookservice.repository.jpa;

import com.web.bookservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long> {

    Member findById(String id);

    boolean existsById(String id);



}

package com.web.bookservice.repository.jpa;

import com.web.bookservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByLoginId(String id);

    boolean existsByLoginId(String id);



}

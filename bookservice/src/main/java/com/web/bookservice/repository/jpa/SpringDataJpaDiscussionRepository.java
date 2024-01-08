package com.web.bookservice.repository.jpa;

import com.web.bookservice.domain.Discussion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaDiscussionRepository extends JpaRepository<Discussion, Long> {

    Page<Discussion> findByTitleContaining(String query, Pageable pageable);

    Page<Discussion> findByBook_TitleContaining(String query, Pageable pageable);

    Page<Discussion> findByMember_NameContaining(String query, Pageable pageable);

    Page<Discussion> findByTitleContainingAndMember_Id(String title, String memberId, Pageable pageable);

    Page<Discussion> findByBook_TitleContainingAndMember_Id(String bookTitle, String memberId, Pageable pageable);

    Page<Discussion> findByMember_NameContainingAndMember_Id(String memberName, String memberId, Pageable pageable);

    Page<Discussion> findByMember_Id(String memberId, Pageable pageable);

}

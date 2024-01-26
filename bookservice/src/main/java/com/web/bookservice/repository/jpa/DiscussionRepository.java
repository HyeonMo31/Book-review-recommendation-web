package com.web.bookservice.repository.jpa;

import com.web.bookservice.domain.Discussion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {

    Page<Discussion> findByTitleContaining(String query, Pageable pageable);

    Page<Discussion> findByBook_TitleContaining(String query, Pageable pageable);

    Page<Discussion> findByMember_NameContaining(String query, Pageable pageable);

    Page<Discussion> findByTitleContainingAndMember_LoginId(String title, String longId, Pageable pageable);

    Page<Discussion> findByBook_TitleContainingAndMember_LoginId(String bookTitle, String longId, Pageable pageable);

    Page<Discussion> findByMember_NameContainingAndMember_LoginId(String memberName, String longId, Pageable pageable);

    Page<Discussion> findByMember_LoginId(String longId, Pageable pageable);

}

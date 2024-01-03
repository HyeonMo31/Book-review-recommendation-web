package com.web.bookservice.repository.jpa;

import com.web.bookservice.domain.Book;
import com.web.bookservice.domain.Bookmark;
import com.web.bookservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaBookMarkRepository extends JpaRepository<Bookmark, Long> {


    boolean existsByBookAndMember(Book book, Member member);

    Bookmark findByBookAndMember(Book book, Member member);
}

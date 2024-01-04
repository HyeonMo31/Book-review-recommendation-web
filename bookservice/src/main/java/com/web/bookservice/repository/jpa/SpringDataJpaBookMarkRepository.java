package com.web.bookservice.repository.jpa;

import com.web.bookservice.domain.Book;
import com.web.bookservice.domain.Bookmark;
import com.web.bookservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaBookMarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByMember(Member member);
    boolean existsByBookAndMember(Book book, Member member);

    Bookmark findByBookAndMember(Book book, Member member);
}

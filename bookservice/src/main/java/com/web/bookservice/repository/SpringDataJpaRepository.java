package com.web.bookservice.repository;

import com.web.bookservice.domain.Book;
import com.web.bookservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaRepository extends JpaRepository<Member, Long> {

    Member findById(String id);

    boolean existsById(String id);

    Book findByIsbn(String isbn);

}

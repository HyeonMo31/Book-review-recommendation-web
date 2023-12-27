package com.web.bookservice.repository;


import com.web.bookservice.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final SpringDataJpaRepository springDataJpaRepository;

    public Book findBookByIsbn(String isbn) {
        return springDataJpaRepository.findByIsbn(isbn);
    }


}

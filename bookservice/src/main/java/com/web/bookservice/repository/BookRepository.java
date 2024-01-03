package com.web.bookservice.repository;


import com.web.bookservice.domain.Book;
import com.web.bookservice.repository.jpa.SpringDataJpaBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final SpringDataJpaBookRepository repository;

    public void save(Book book) {
        repository.save(book);
    }

    public Book findBookByIsbn(String isbn) {
        return repository.findByIsbn(isbn);
    }

    public boolean existsBookByIsbn(String isbn) { return repository.existsByIsbn(isbn);}


}

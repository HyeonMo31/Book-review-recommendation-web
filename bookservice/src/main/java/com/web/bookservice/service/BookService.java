package com.web.bookservice.service;

import com.web.bookservice.domain.Book;
import com.web.bookservice.domain.Member;
import com.web.bookservice.repository.BookRepository;
import com.web.bookservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public void save(Book book){
        if(validateDuplicateBook(book.getIsbn()))
            return;
        bookRepository.save(book);
    }

    public Book findBookByIsbn(String isbn) {
        return bookRepository.findBookByIsbn(isbn);
    }

    public boolean validateDuplicateBook(String isbn) {
        if(bookRepository.existsBookByIsbn(isbn)) {
            return true;
        } else {
            return false;
        }
    }


}

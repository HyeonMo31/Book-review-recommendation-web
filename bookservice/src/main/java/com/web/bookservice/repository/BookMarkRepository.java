package com.web.bookservice.repository;

import com.web.bookservice.domain.Book;
import com.web.bookservice.domain.Bookmark;
import com.web.bookservice.domain.Member;
import com.web.bookservice.repository.jpa.SpringDataJpaBookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookMarkRepository {

    private final SpringDataJpaBookMarkRepository repository;

    public void save(Bookmark bookmark) {
        repository.save(bookmark);
    }

    public boolean existsByBookAndMember(Book book, Member member) {
        return repository.existsByBookAndMember(book, member);
    }

    public Bookmark findByBookAndMember(Book book, Member member) {
        return repository.findByBookAndMember(book, member);
    }

    public void removeBookmark(Bookmark bookmark) {
        repository.delete(bookmark);
    }



}

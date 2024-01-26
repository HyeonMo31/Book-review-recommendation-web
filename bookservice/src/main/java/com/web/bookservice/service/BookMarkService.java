package com.web.bookservice.service;

import com.web.bookservice.domain.Book;
import com.web.bookservice.domain.Bookmark;
import com.web.bookservice.domain.Member;
import com.web.bookservice.repository.jpa.BookMarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookMarkService {

    private final BookMarkRepository repository;

    public void save(Book book, Member member) {

        Bookmark bookmark = new Bookmark();

        bookmark.setMember(member);
        bookmark.setBook(book);

        if(validateDuplicateBookmark(book, member))
            return;

        repository.save(bookmark);
    }

    public boolean existsByMemberAndBook(Book book, Member member) {
        return repository.existsByBookAndMember(book, member);
    }

    public Bookmark findByMemberAndBook(Book book, Member member) {
        return repository.findByBookAndMember(book, member);
    }

    public boolean validateDuplicateBookmark(Book book, Member member) {
        return repository.existsByBookAndMember(book, member);
    }

    public void removeBookmark(Book book, Member member) {

        Bookmark bookmark = findByMemberAndBook(book, member);

        repository.delete(bookmark);
    }

    public List<Bookmark> findByMember(Member member) {
        return repository.findByMember(member);
    }


}

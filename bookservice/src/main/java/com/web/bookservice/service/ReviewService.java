package com.web.bookservice.service;

import com.web.bookservice.domain.Book;
import com.web.bookservice.domain.Member;
import com.web.bookservice.domain.Review;
import com.web.bookservice.repository.jpa.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository repository;

    public void save(Book book, Member member, String write)
    {
        Review review = new Review();

        review.setBook(book);
        review.setMember(member);
        review.setText(write);
        review.setWriteDate(LocalDateTime.now());

        repository.save(review);
    }

    public List<Review> findByBook(Book book) {
        return repository.findByBook(book);
    }

    public Review findById(Long id) {
        return repository.findById(id).get();
    }

    public void deleteReview(Review review) {
        repository.delete(review);
    }



}

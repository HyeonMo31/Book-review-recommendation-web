package com.web.bookservice.service;

import com.web.bookservice.domain.Book;
import com.web.bookservice.domain.Member;
import com.web.bookservice.domain.Review;
import com.web.bookservice.repository.ReviewRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepositroy repositroy;

    public void save(Book book, Member member, String write)
    {
        Review review = new Review();

        review.setBook(book);
        review.setMember(member);
        review.setText(write);
        review.setWriteDate(LocalDateTime.now());

        repositroy.save(review);
    }

    public List<Review> getReviewsByBook(Book book) {
        return repositroy.getReviewsByBook(book);
    }

    public Review findByIndex(Long index) {
        return repositroy.findByIndex(index);
    }

    public void deleteReview(Long reviewIndex) {
        repositroy.deleteReview(reviewIndex);
    }



}

package com.web.bookservice.repository;

import com.web.bookservice.domain.Book;
import com.web.bookservice.domain.Review;
import com.web.bookservice.repository.jpa.SpringDataJpaReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositroy {

    private final SpringDataJpaReviewRepository repository;

    public void save(Review review) {
        repository.save(review);
    }

    public List<Review> getReviewsByBook(Book book) {
        return repository.findByBook(book);
    }

    public Review findByIndex(Long index) {
        return  repository.findByReviewIndex(index);
    }

    public void deleteReview(Long reivewIndex) {
        repository.deleteById(reivewIndex);
    }


}

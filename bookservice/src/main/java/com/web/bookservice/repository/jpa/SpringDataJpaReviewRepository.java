package com.web.bookservice.repository.jpa;

import com.web.bookservice.domain.Book;
import com.web.bookservice.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByBook(Book book);

    Review findByReviewIndex(Long index);

}

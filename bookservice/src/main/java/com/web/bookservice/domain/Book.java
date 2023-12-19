package com.web.bookservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Book {

    @Id
    @GeneratedValue
    private Long bookIndex;

    private String text;
    private double rate;
    private String author;
    private LocalDateTime publicationDate;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Discussion> discussions;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks;
}

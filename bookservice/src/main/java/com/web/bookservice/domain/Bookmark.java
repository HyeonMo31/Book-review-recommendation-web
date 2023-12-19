package com.web.bookservice.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Bookmark {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_index")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_index")
    private Book book;
}

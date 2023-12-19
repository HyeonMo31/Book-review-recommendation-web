package com.web.bookservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Review {

    @Id
    @GeneratedValue
    private Long reviewIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_index")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="book_index")
    private Book book;

    private int startCount;
    private LocalDateTime writeDate;
    private String text;


}

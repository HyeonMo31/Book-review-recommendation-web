package com.web.bookservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Discussion {

    @Id
    @GeneratedValue
    private Long discussionIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_index")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_index")
    private Book book;

    private String title;
    private String text;
    private LocalDateTime writeDate;

    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL)
    private List<Comment> comments;


}

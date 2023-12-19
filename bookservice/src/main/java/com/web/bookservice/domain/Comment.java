package com.web.bookservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Comment {

    @Id
    @GeneratedValue
    private Long commentIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discussion_index")
    private Discussion discussion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_index")
    private Member member;

    private String text;
    private LocalDateTime writeDate;


}

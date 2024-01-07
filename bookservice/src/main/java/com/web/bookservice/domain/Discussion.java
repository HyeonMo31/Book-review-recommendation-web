package com.web.bookservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@ToString
public class Discussion {

    @Id
    @GeneratedValue
    private Long discussionIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_index")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_index")
    @NotNull(message = "책을 필수로 선택하십시오.")
    private Book book;

    @NotBlank(message = "제목을 입력해 주세요.")
    private String title;

    @NotBlank(message = "텍스트를 입력해주세요.")
    @Column(columnDefinition = "TEXT")
    private String text;

    private LocalDate writeDate;

    @OneToMany(mappedBy = "discussion", cascade = CascadeType.ALL)
    private List<Comment> comments;


}

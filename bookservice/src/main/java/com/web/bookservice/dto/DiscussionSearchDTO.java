package com.web.bookservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DiscussionSearchDTO {

    private Long id;
    private String title;
    private String bookTitle;
    private String writer;
    private LocalDate writeDate;
}

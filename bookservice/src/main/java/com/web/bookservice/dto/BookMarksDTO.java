package com.web.bookservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookMarksDTO{

    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;


}

package com.web.bookservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@ToString
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    //      Column 애너테이션의 length를 설정하지 않으면, default로 CHARACTER VARYING(255)로 설정되는 것 같다.
    // 그래서 디스크립션에서 255 초과했다고 오류가 났다.
    //해결법은 도메인에서  columnDefinition = "TEXT 추가
    @Column(columnDefinition = "TEXT")
    private String description;

    private double rate;
    private String author;
    private String publisher;
    private String image;
    private String isbn;


    //어노테이션을 사용하여 날짜 형식을 "yyyyMMdd"로 지정하였습니다.
    //2011-11-48로 매핑하면 쥰내 귀찮음.
//    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate pubdate;


}

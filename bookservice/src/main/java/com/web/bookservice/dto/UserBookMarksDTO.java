package com.web.bookservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserBookMarksDTO {

    private String name;
    List<BookMarksDTO> bookMarksDTOList = new ArrayList<>();

}

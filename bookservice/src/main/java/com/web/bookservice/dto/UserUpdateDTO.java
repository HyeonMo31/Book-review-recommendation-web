package com.web.bookservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {

    private String name;
    private String city;
    private String password;
}

package com.web.bookservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class UserDTO {

    private String name;
    private String loginId;
    private String city;
    private LocalDateTime joinDate;
}

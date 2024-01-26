package com.web.bookservice.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginMember {

    @NotBlank
    private String loginId;
    @NotBlank
    private String password;
}

package com.web.bookservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    private Long memberIndex;

    @NotBlank
    private String name;
    @NotBlank
    private String id;
    @NotBlank
    private String password;

    private LocalDateTime joinDate;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Discussion> discussions;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks;

}

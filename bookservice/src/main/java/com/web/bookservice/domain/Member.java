package com.web.bookservice.domain;

import jakarta.persistence.*;
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

    private String name;
    private String id;
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

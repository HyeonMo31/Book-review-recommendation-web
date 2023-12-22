package com.web.bookservice.repository;

import com.web.bookservice.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class MemberRepository {


    private final SpringDataJpaMemberRepository repository;

    public void save(Member member) {
        member.setJoinDate(LocalDateTime.now());
        repository.save(member);
    }

    public boolean findById(String id) {
        return repository.existsById(id);
    }

}

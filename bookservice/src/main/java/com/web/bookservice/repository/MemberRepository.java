package com.web.bookservice.repository;

import com.web.bookservice.domain.Member;
import com.web.bookservice.repository.jpa.SpringDataJpaMemberRepository;
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

    public boolean existsMemberById(String id) {
        return repository.existsById(id);
    }

    public Member findMemberById(String id) {
        return repository.findById(id);
    }


}

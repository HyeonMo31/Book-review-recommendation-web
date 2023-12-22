package com.web.bookservice.service;

import com.web.bookservice.domain.Member;
import com.web.bookservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(Member member){

        validateDuplicateMember(member.getId());

        memberRepository.save(member);
    }

    public void validateDuplicateMember(String id) {
        if(memberRepository.findById(id))
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
    }

}

package com.web.bookservice.service;

import com.web.bookservice.domain.Member;
import com.web.bookservice.repository.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(Member member){

        validateDuplicateMember(member.getLoginId());

        memberRepository.save(member);
    }

    public Member login(String loginId, String password) {

        if(memberRepository.existsByLoginId(loginId) ) {

            Member findMember = memberRepository.findByLoginId(loginId);
//                여기서 계속헤맸지 ㅋㅋ String 비교 !! 제발
//            if(findMember.getPassword() == password) {
            if(findMember.getPassword().equals(password)) {
                return findMember;
            }
        } else {
            return null;
        }
        return null;
    }

    public void validateDuplicateMember(String id) {
        if(memberRepository.existsByLoginId(id))
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
    }

    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

}

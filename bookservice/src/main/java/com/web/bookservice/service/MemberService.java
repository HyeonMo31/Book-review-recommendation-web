package com.web.bookservice.service;

import com.web.bookservice.domain.Member;
import com.web.bookservice.repository.jpa.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void join(Member member){

        validateDuplicateMember(member.getLoginId());

        member.setJoinDate(LocalDateTime.now());

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

    public void validateDuplicateMember(String LoginId) {
        if(memberRepository.existsByLoginId(LoginId))
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
    }

    public boolean validatePassword(String password, Member member) {

        if(member.getPassword().equals(password))
            return true;
        else
            return false;
    }

    public void profileUpdate(Member member, String name, String city) {

        Optional<Member> findMember = memberRepository.findById(member.getId());

        log.info("name={}", name);
        log.info("city={}", city);
        findMember.get().setName(name);
        findMember.get().setCity(city);

    }

    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

}

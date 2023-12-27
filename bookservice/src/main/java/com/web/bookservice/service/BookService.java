package com.web.bookservice.service;

import com.web.bookservice.domain.Book;
import com.web.bookservice.domain.Member;
import com.web.bookservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final MemberRepository memberRepository;

    public void join(Member member){

        validateDuplicateMember(member.getId());

        memberRepository.save(member);
    }

    public Member login(String loginId, String password) {

        if(memberRepository.existsMemberById(loginId) ) {

            Member findMember = memberRepository.findMemberById(loginId);
            log.info("findMemberId={}, findMemberPassword={}", findMember.getId(), findMember.getPassword());
//                여기서 계속헤맸지 ㅋㅋ String 비교 !! 제발
//            if(findMember.getPassword() == password) {
            if(findMember.getPassword().equals(password)) {
                log.info("리포지토리에서 찾은 아이디에 대한 비밀번호={}, 폼에서 들어온 비밀번호={}", findMember.getPassword(), password);
                return findMember;
            }
        } else {
            return null;
        }
        return null;
    }


    public void validateDuplicateMember(String id) {
        if(memberRepository.existsMemberById(id))
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
    }

}

package com.web.bookservice;

import com.web.bookservice.domain.Member;
import com.web.bookservice.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository repository;

    @PostConstruct
    public void testDataAdd() {
        Member member = new Member();
        member.setId("thesting31");
        member.setName("정현모");
        member.setPassword("31");

        repository.save(member);
    }
}

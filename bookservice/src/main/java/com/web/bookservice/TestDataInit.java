package com.web.bookservice;

import com.web.bookservice.domain.Book;
import com.web.bookservice.domain.Bookmark;
import com.web.bookservice.domain.Discussion;
import com.web.bookservice.domain.Member;
import com.web.bookservice.repository.jpa.BookMarkRepository;
import com.web.bookservice.repository.jpa.BookRepository;
import com.web.bookservice.repository.jpa.DiscussionRepository;
import com.web.bookservice.repository.jpa.MemberRepository;
import com.web.bookservice.service.DiscussionService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class TestDataInit {

    private MemberRepository memberRepository;
    private BookRepository bookRepository;
    private BookMarkRepository bookMarkRepository;
    private DiscussionRepository discussionRepository;

    public TestDataInit(MemberRepository memberRepository, BookRepository bookRepository,
                        BookMarkRepository bookMarkRepository, DiscussionRepository discussionRepository) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
        this.bookMarkRepository = bookMarkRepository;
        this.discussionRepository = discussionRepository;
    }

    @PostConstruct
    public void testDataAdd() {

        Member member = new Member();
        member.setLoginId("thesting31");
        member.setName("정현모");
        member.setPassword("31");
        member.setCity("전주");
        member.setJoinDate(LocalDateTime.now());

        memberRepository.save(member);

        Book book = new Book();
        book.setTitle("정현모의 일기");
        book.setAuthor("정현모");
        book.setIsbn("123");
        book.setDescription("나도 몰라");
        book.setPublisher("정현모 머릿속");
        book.setPubdate(LocalDate.now());

        bookRepository.save(book);

        for(int i = 0; i < 11; i++) {
            Discussion discussion = new Discussion();
            discussion.setWriteDate(LocalDate.now());
            discussion.setMember(member);
            discussion.setBook(book);
            discussion.setText("d");
            discussion.setTitle("dddd");
            discussionRepository.save(discussion);
        }







    }
}

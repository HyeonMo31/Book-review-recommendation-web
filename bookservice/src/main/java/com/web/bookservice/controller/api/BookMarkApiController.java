package com.web.bookservice.controller.api;

import com.web.bookservice.domain.Bookmark;
import com.web.bookservice.domain.Member;
import com.web.bookservice.dto.BookMarksDTO;
import com.web.bookservice.dto.UserBookMarksDTO;
import com.web.bookservice.service.BookMarkService;
import com.web.bookservice.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class BookMarkApiController {

    private BookMarkService bookMarkService;
    private MemberService memberService;

    public BookMarkApiController(BookMarkService bookMarkService, MemberService memberService) {
        this.bookMarkService = bookMarkService;
        this.memberService = memberService;
    }

    @GetMapping("/api/bookmarks/{loginId}")
    public ResponseEntity<?> getUserBookMarks(@PathVariable("loginId")String loginId) {

        Member member = memberService.findByLoginId(loginId);

        if(member == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저를 찾을 수 없습니다.");

        UserBookMarksDTO userBookMarksDTO = new UserBookMarksDTO();

        userBookMarksDTO.setName(member.getName());
        List<Bookmark> bookmarks = bookMarkService.findByMember(member);

        for(Bookmark bookmark : bookmarks) {
            BookMarksDTO bookMarksDTO = new BookMarksDTO();
            bookMarksDTO.setIsbn(bookmark.getBook().getIsbn());
            bookMarksDTO.setId(bookmark.getId());
            bookMarksDTO.setTitle(bookmark.getBook().getTitle());
            bookMarksDTO.setAuthor(bookmark.getBook().getAuthor());
            bookMarksDTO.setPublisher((bookmark.getBook().getPublisher()));
            userBookMarksDTO.getBookMarksDTOList().add(bookMarksDTO);
        }

        return ResponseEntity.ok(userBookMarksDTO);
    }

}

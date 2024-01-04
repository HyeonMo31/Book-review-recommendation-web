package com.web.bookservice.controller;

import com.web.bookservice.domain.Bookmark;
import com.web.bookservice.domain.Discussion;
import com.web.bookservice.domain.Member;
import com.web.bookservice.service.BookMarkService;
import com.web.bookservice.service.BookService;
import com.web.bookservice.service.DiscussionService;
import com.web.bookservice.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
public class DiscussionController {

    private BookMarkService bookMarkService;
    private DiscussionService discussionService;

    public DiscussionController(BookMarkService bookMarkService,
                                DiscussionService discussionService) {
        this.bookMarkService = bookMarkService;
        this.discussionService = discussionService;
    }

    @GetMapping("/discussion")
    public String discussionList() {
        return "discussion/discussionList";
    }

    @GetMapping("/discussion/add")
//    @Transactional
    public String discussionAddForm (Model model, HttpServletRequest request) {

        //현재 로그인한 사용자의 북마크 목록을 가져와야한다.
        Member member = (Member) request.getSession(false).getAttribute("user");

//        List<Bookmark> bookmarkList = member.getBookmarks();
        List<Bookmark> bookmarkList = bookMarkService.findByMember(member);

        model.addAttribute("discussion", new Discussion());
        model.addAttribute("bookmarks", bookmarkList);
        return "discussion/addForm";
    }

    @PostMapping("/discussion/add")
    public String discussionAddForm (@Validated  @ModelAttribute Discussion discussion, BindingResult result,
                                     HttpServletRequest request) {

        if(result.hasErrors()) {
            return "discussion/addForm";
        }

        Member member = (Member) request.getSession(false).getAttribute("user");
        discussion.setMember(member);
        discussion.setWriteDate(LocalDateTime.now());
        discussionService.save(discussion);

        return "redirect:/discussion";
    }
}

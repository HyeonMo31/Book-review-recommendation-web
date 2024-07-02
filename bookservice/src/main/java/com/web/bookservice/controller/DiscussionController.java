package com.web.bookservice.controller;

import com.web.bookservice.domain.Bookmark;
import com.web.bookservice.domain.Comment;
import com.web.bookservice.domain.Discussion;
import com.web.bookservice.domain.Member;
import com.web.bookservice.service.BookMarkService;
import com.web.bookservice.service.CommentService;
import com.web.bookservice.service.DiscussionService;
import com.web.bookservice.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class DiscussionController {

    private BookMarkService bookMarkService;
    private DiscussionService discussionService;
    private CommentService commentService;
    private MemberService memberService;

    public DiscussionController(BookMarkService bookMarkService,
                                DiscussionService discussionService,
                                CommentService commentService,
                                MemberService memberService) {
        this.bookMarkService = bookMarkService;
        this.discussionService = discussionService;
        this.commentService = commentService;
        this.memberService = memberService;
    }


    @GetMapping("/discussion/list")
    public String discussionList(@RequestParam(defaultValue = "0", name = "page")int page,
            @RequestParam(name = "select", required = false) String select,
            @RequestParam(name = "query", required = false) String query,
            @RequestParam(name = "myDiscussion", required = false, defaultValue = "false") boolean myDiscussion,
                                 HttpServletRequest request,
                                 Model model) {

        int pageSize = 10;

        log.info("myDiscussion={}", myDiscussion);

        Page<Discussion> pageResult;

        if(myDiscussion) {

            Member member = (Member)request.getSession(false).getAttribute("user");
            pageResult = discussionService.getDataByPageAndMember(member.getLoginId(),select, query ,page, pageSize);
        } else {
            pageResult = discussionService.getDataByPage(select, query ,page, pageSize);
        }



        long start = pageResult.getPageable().getOffset()+1;
        long end = (start - 1) + pageResult.getNumberOfElements();
        log.info("start={}", start);

        //페이지 개수 넘겨야 되니까.

        model.addAttribute("pageCount", pageResult.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("discussions", pageResult);
        model.addAttribute("totalSize", pageResult.getTotalElements());
        model.addAttribute("start", start);
        model.addAttribute("end", end);

        return "discussion/discussionList";
    }

    @GetMapping("/discussion/myList")
    public String discussionMyList(RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("myDiscussion", true);

        return "redirect:/discussion/list";
    }


    @GetMapping("/discussion/add")
    public String discussionAddForm (Model model, HttpServletRequest request) {

        //현재 로그인한 사용자의 북마크 목록을 가져와야한다.
        Member member = (Member) request.getSession(false).getAttribute("user");
        //아래 코드는 lazy 로딩오류가 나는 코드이다.
//        List<Bookmark> bookmarkList = member.getBookmarks();
        Member findMember = memberService.findByLoginId(member.getLoginId());
        List<Bookmark> bookmarkList = bookMarkService.findByMember(member);

        model.addAttribute("name", findMember.getName());
//        model.addAttribute("name", member.getName());
        model.addAttribute("discussion", new Discussion());
        model.addAttribute("bookmarks", bookmarkList);
        return "discussion/addForm";
    }

    @PostMapping("/discussion/add")
    public String discussionAddForm (@Validated  @ModelAttribute Discussion discussion, BindingResult result,
                                     HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

        Member member = (Member) request.getSession(false).getAttribute("user");

        if(result.hasErrors()) {
            List<Bookmark> bookmarkList = bookMarkService.findByMember(member);
            model.addAttribute("bookmarks", bookmarkList);
            return "discussion/addForm";
        }


        discussion.setMember(member);
        discussion.setWriteDate(LocalDate.now());
        Long discussionId = discussionService.save(discussion);


        redirectAttributes.addAttribute("discussionId", discussionId);

        return "redirect:/discussion/post/{discussionId}";
    }

    @GetMapping("discussion/post/{discussionId}")
    public String postDetail(@PathVariable(name = "discussionId")Long discussionId, Model model) {

        //토론 상세내용
        Discussion discussion = discussionService.findById(discussionId);

        List<Comment> comments;
        //토론 댓글 리스트.
        comments = commentService.findByDiscussoin(discussion);



        model.addAttribute("comments", comments);
        model.addAttribute("discussion", discussion);

        return "discussion/post";
    }

    @PostMapping("/discussion/comment/add/post/{discussionId}")
    public String addComment(@PathVariable("discussionId")Long discussionId, @RequestParam("comment") String text,
                             HttpServletRequest request, RedirectAttributes redirectAttributes){


        //코멘트 저장하기
        Member member = (Member) request.getSession(false).getAttribute("user");
        Discussion discussion = discussionService.findById(discussionId);

        commentService.save(discussion ,member, text);


        redirectAttributes.addAttribute("discussionId", discussionId);

        return "redirect:/discussion/post/{discussionId}";
    }

    @DeleteMapping("/discussion/delete/post/{discussionId}")
    public ResponseEntity<String> deleteDiscussion(@PathVariable("discussionId") Long discussionId) {

        try {
            Discussion discussion = discussionService.findById(discussionId);
            discussionService.deleteDiscussion(discussion);
            return ResponseEntity.ok("글이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생하였습니다.");
        }

    }

    @GetMapping("/discussion/update/post/{discussionId}")
    public String editFormDiscussion(@PathVariable("discussionId") Long discussionId, Model model) {

        Discussion discussion = discussionService.findById(discussionId);

        model.addAttribute("discussion", discussion);

        return "discussion/editForm";

    }

    @PostMapping("/discussion/update/post/{discussionId}")
    public String editDiscussion(@RequestParam("title")String title, @RequestParam("text")String text,
                                 @PathVariable("discussionId") Long discussionId,
                                 RedirectAttributes redirectAttributes) {

        discussionService.update(discussionId, title, text);

        redirectAttributes.addAttribute("discussionId", discussionId);
        return "redirect:/discussion/post/{discussionId}";

    }

    @DeleteMapping("/discussion/delete/comment/{commentIndex}/post/{discussionIndex}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentIndex")Long commentIndex, @PathVariable("discussionIndex") Long discussionIndex) {

        try {
            Comment comment = commentService.findByComment(commentIndex);
            commentService.deleteComment(comment);
            return ResponseEntity.ok("댓글이 삭제 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생하였습니다.");
        }


    }


}

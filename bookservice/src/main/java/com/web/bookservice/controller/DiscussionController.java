package com.web.bookservice.controller;

import com.web.bookservice.domain.Bookmark;
import com.web.bookservice.domain.Comment;
import com.web.bookservice.domain.Discussion;
import com.web.bookservice.domain.Member;
import com.web.bookservice.service.BookMarkService;
import com.web.bookservice.service.CommentService;
import com.web.bookservice.service.DiscussionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class DiscussionController {

    private BookMarkService bookMarkService;
    private DiscussionService discussionService;
    private CommentService commentService;

    public DiscussionController(BookMarkService bookMarkService,
                                DiscussionService discussionService,
                                CommentService commentService) {
        this.bookMarkService = bookMarkService;
        this.discussionService = discussionService;
        this.commentService = commentService;
    }

    @GetMapping("/discussion/list")
    public String discussionList(Model model) {

        List<Discussion> discussions = discussionService.findAll();

        model.addAttribute("discussions", discussions);

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
                                     HttpServletRequest request, RedirectAttributes redirectAttributes) {

        if(result.hasErrors()) {
            return "discussion/addForm";
        }

        Member member = (Member) request.getSession(false).getAttribute("user");
        discussion.setMember(member);
        discussion.setWriteDate(LocalDate.now());
        Long discussionId = discussionService.save(discussion);

        log.info("dissid={}", discussionId);

        redirectAttributes.addAttribute("discussionId", discussionId);

        return "redirect:/discussion/post/{discussionId}";
    }

    @GetMapping("discussion/post/{discussionId}")
    public String postDetail(@PathVariable(name = "discussionId")Long discussionId, Model model) {

        //토론 상세내용
        Optional<Discussion> discussion = discussionService.findById(discussionId);

            List<Comment> comments;
        //토론 댓글 리스트.
        if(discussion.isPresent()) {
             comments = discussion.get().getComments();;
        } else {
            comments = null;
        }


        model.addAttribute("comments", comments);
        model.addAttribute("discussion", discussion);

        return "discussion/post";
    }

    @PostMapping("/discussion/comment/add/post/{discussionId}")
    public String addComment(@PathVariable("discussionId")Long discussionId, @RequestParam("comment") String text,
                             HttpServletRequest request, RedirectAttributes redirectAttributes){


        //코멘트 저장하기
        Member member = (Member) request.getSession(false).getAttribute("user");
        Optional<Discussion> discussion = discussionService.findById(discussionId);

        commentService.save(discussion ,member, text);


        redirectAttributes.addAttribute("discussionId", discussionId);

        return "redirect:/discussion/post/{discussionId}";
    }

    @DeleteMapping("/discussion/delete/post/{discussionId}")
    public ResponseEntity<String> deleteDiscussion(@PathVariable("discussionId") Long discussionId) {

        try {
            discussionService.deleteDiscussion(discussionId);
            return ResponseEntity.ok("글이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생하였습니다.");
        }

    }

    @GetMapping("/discussion/update/post/{discussionId}")
    public String editFormDiscussion(@PathVariable("discussionId") Long discussionId, Model model) {

        Optional<Discussion> findDiscussion = discussionService.findById(discussionId);
//
//        String text = discussion.get().getText();
//        String title = discussion.get().getTitle();
//
//        model.addAttribute("title", title);
//        model.addAttribute("text", text);
        if(findDiscussion.isPresent()) {
            log.info("존재한다.");
            Discussion discussion = findDiscussion.get();

            log.info("discussio={}", discussion.getText());
            log.info("discussio={}", discussion.getTitle());


            model.addAttribute("discussion", discussion);
        } else {
            log.info("존재하지 않는다.");
        }



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
            commentService.deleteComment(commentIndex);
            return ResponseEntity.ok("댓글이 삭제 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생하였습니다.");
        }


    }


}

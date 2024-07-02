package com.web.bookservice.controller;

import com.web.bookservice.domain.*;
import com.web.bookservice.service.BookMarkService;
import com.web.bookservice.service.DiscussionService;
import com.web.bookservice.service.MemberService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class MemberController {

    private MemberService memberService;
    private DiscussionService discussionService;
    private BookMarkService bookMarkService;

    public MemberController(MemberService memberService, DiscussionService discussionService, BookMarkService bookMarkService) {
        this.memberService = memberService;
        this.discussionService = discussionService;
        this.bookMarkService = bookMarkService;
    }


    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginMember")LoginMember loginMember) {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginMember loginMember, BindingResult result, HttpServletRequest request) {


        if(result.hasErrors()) {
            return "user/login";
        }

        Member member = memberService.login(loginMember.getLoginId(), loginMember.getPassword());

        if(member == null) {

            result.reject("notFound", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "user/login";
        }

        HttpSession session = request.getSession();

        //세션이름을 뭘로 해야될까나?
        session.setAttribute("user",member);

        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm(@ModelAttribute("member")Member member) {
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute Member member, BindingResult result, RedirectAttributes
            redirectAttributes){

        if(result.hasErrors()) {
            return "user/register";
        }

        try {
            memberService.join(member);
            redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료 되었습니다.");
        } catch (IllegalStateException e) {
            result.reject("id", "이미 존재하는 아이디입니다.");
            return "user/register";
        }
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }


    @GetMapping("/api/users/{loginId}")
    @ResponseBody
    public ResponseEntity<UserDTO> getUserInfo(HttpServletRequest request, @PathVariable("loginId") String loginId) {

        Member findMember = memberService.findByLoginId(loginId);

        UserDTO userDTO = new UserDTO();
        userDTO.setCity(findMember.getCity());
        userDTO.setName(findMember.getName());
        userDTO.setJoinDate(findMember.getJoinDate());
        userDTO.setLoginId(findMember.getLoginId());

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/profile")
    public String profileForm(HttpServletRequest request, Model model) {


        Member member = (Member)request.getSession(false).getAttribute("user");

        model.addAttribute("loginId", member.getLoginId());

        return "user/profile/profile";
    }

//    @PostMapping("/profile")
//    public  String profileUpdate(UserDTO userDTO, HttpServletRequest request, RedirectAttributes redirectAttributes) {
//
//        Member member = (Member) request.getSession(false).getAttribute("user");
//
//        String password = userDTO.getPassword();
//
//        if(!memberService.validatePassword(password, member)) {
//            redirectAttributes.addFlashAttribute("fail", true);
//           return "redirect:/profile";
//        }
//
//        String name = userDTO.getName();
//        String city = userDTO.getCity();
//
//        memberService.profileUpdate(memberService.findByLoginId(member.getLoginId()), name, city);
//
//        redirectAttributes.addFlashAttribute("success", true);
//
//        return "redirect:/profile";
//    }

    @GetMapping("/profile/discussionList")
    public String userDiscussionList(@RequestParam(defaultValue = "0", name = "page")int page,
                                     @RequestParam(name = "select", required = false) String select,
                                     @RequestParam(name = "query", required = false) String query,
                                     HttpServletRequest request,
                                     Model model) {

        Member member = (Member)request.getSession(false).getAttribute("user");

        Member findMember = memberService.findByLoginId(member.getLoginId());

        int pageSize = 10;

        Page<Discussion> pageResult;
        pageResult = discussionService.getDataByPageAndMember(findMember.getLoginId(),select, query ,page, pageSize);


        long start = pageResult.getPageable().getOffset()+1;
        long end = (start - 1) + pageResult.getNumberOfElements();


        model.addAttribute("pageCount", pageResult.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("discussions", pageResult);
        model.addAttribute("totalSize", pageResult.getTotalElements());
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("member", findMember);


        return "user/profile/userDiscussionList";
    }

    @GetMapping("/profile/bookmarkList")
    public String userBookmarkList(@RequestParam(defaultValue = "0", name = "page")int page,
                                             HttpServletRequest request,
                                             Model model) {

        Member member = (Member)request.getSession(false).getAttribute("user");

        Member findMember = memberService.findByLoginId(member.getLoginId());

        int pageSize = 10;

        Page<Bookmark> pageResult;
        pageResult = bookMarkService.findByMember(member, page, pageSize);

        long start = pageResult.getPageable().getOffset()+1;
        long end = (start - 1) + pageResult.getNumberOfElements();

        model.addAttribute("pageCount", pageResult.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("bookmarks", pageResult);
        model.addAttribute("totalSize", pageResult.getTotalElements());
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("member", findMember);


        return "user/profile/userBookmarkList";
    }
}

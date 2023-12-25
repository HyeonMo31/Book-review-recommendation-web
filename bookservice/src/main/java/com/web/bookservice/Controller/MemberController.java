package com.web.bookservice.Controller;

import com.web.bookservice.domain.LoginMember;
import com.web.bookservice.domain.Member;
import com.web.bookservice.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginMember")LoginMember loginMember) {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginMember loginMember, BindingResult result, HttpServletRequest request) {


        if(result.hasErrors()) {
            log.info("무슨 에러가 있다는 것?={}", result);
            return "user/login";
        }

        Member member = memberService.login(loginMember.getId(), loginMember.getPassword());
        //rejecValue 안쓰면 에러코드 다음에 바로 디폴트 메시지 가능이구먼.
        if(member == null) {

            result.reject("notFound", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "user/login";
        }

        HttpSession session = request.getSession();

        //세션이름을 뭘로 해야될까나?
        session.setAttribute("user",loginMember);


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
            result.rejectValue("id", null, null, "이미 존재하는 아이디입니다.");
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

    @GetMapping("/mypage")
    public String mypageForm(@ModelAttribute Member member, Model model, HttpServletRequest request) {
        log.info("mypage에서 세션 확인={}", request.getAttribute("isLogin"));
        log.info("mypage에서 모델 값 확인={}", model.asMap());
        return "user/mypage";
    }
}

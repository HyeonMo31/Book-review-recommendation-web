package com.web.bookservice.Controller;

import com.web.bookservice.domain.Member;
import com.web.bookservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
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
}

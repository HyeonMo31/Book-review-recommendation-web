package com.web.bookservice.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ModelAttribute
    public void login(Model model, HttpServletRequest request){
        boolean isLogin;

        HttpSession session = request.getSession(false);

        if(session == null ||session.getAttribute("user") == null) {
            isLogin = false;
        } else {
            isLogin = true;
        }

        model.addAttribute("isLogin", isLogin);
    }
}

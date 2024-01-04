package com.web.bookservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiscussionController {

    @GetMapping("/discussion")
    public String discussionList() {
        return "discussion/discussionList";
    }
}

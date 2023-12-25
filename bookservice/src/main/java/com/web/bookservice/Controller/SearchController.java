package com.web.bookservice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {

    @GetMapping("/search")
    public String bookSearch() {
        return "search/bookSearch";
    }

}

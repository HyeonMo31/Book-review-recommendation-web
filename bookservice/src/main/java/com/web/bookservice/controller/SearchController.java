package com.web.bookservice.controller;

import com.web.bookservice.api.BookParsing;
import com.web.bookservice.api.BookSearchAPI;
import com.web.bookservice.domain.Book;
import com.web.bookservice.domain.Member;
import com.web.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SearchController {

    private final BookService bookService;

    @GetMapping("/search")
    public String search() {
        return "search/bookSearch";
    }

    //검색 질의를 보낼텐데 그럼 POST로 보내야하나?
    //그럴필요 없다. GET방식으로 해도된다. POST로 하면 오히려 새로고침했을때
    //양식 다시 제출 알림이 계속 뜨게된다.
    //중요한 정보를 보낼때를 제외하고 검색같은거는 GET으로 매핑하자

    //페이지 번호 버튼 동적 생성에서 @RequestParam defaultValue만 설정했더니 오류뜸. name도 설정해주어야 함.
    @GetMapping("/search/books")
    public String searchBooks(@RequestParam(name = "query") String query,
                              @RequestParam(defaultValue = "1", name = "page") int page,
                              Model model)
    {
        List<BookParsing> books = new ArrayList<>();
        BookSearchAPI bookSearchAPI = new BookSearchAPI();
//        model.addAttribute("result", bookSearchAPI.func("박지원"));
        books = bookSearchAPI.func(query);

        //페이지 번호 버튼 동적 생성

        int pageCount = (int)Math.ceil((double)books.size()/10);
        List<BookParsing> calBooks = new ArrayList<>();
        for(int i = (page-1) * 10; i < page*10; i++) {

            log.info("book.size()={}",books.size());

            if(i >= books.size())
                break;
            calBooks.add(books.get(i));
        }

        model.addAttribute("queryString", query);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("books", calBooks);

        //
        return "search/books";
    }



}

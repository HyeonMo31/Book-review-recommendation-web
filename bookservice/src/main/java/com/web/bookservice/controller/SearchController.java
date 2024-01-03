package com.web.bookservice.controller;

import com.web.bookservice.api.BookParsing;
import com.web.bookservice.api.BookSearchAPI;
import com.web.bookservice.domain.Book;
import com.web.bookservice.domain.Bookmark;
import com.web.bookservice.domain.Member;
import com.web.bookservice.domain.Review;
import com.web.bookservice.service.BookMarkService;
import com.web.bookservice.service.BookService;
import com.web.bookservice.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class SearchController {

    private BookService bookService;
    private BookSearchAPI bookSearchAPI;
    private ReviewService reviewService;
    private BookMarkService bookMarkService;

    public SearchController(BookService bookService, BookSearchAPI bookSearchAPI, ReviewService reviewService,
                            BookMarkService bookMarkService){
        this.bookSearchAPI = bookSearchAPI;
        this.bookService = bookService;
        this.reviewService = reviewService;
        this.bookMarkService = bookMarkService;
    }


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
        List<Book> books = new ArrayList<>();
        books = bookSearchAPI.func(query);

        //페이지 번호 버튼 동적 생성

        int pageCount = (int)Math.ceil((double)books.size()/10);
        List<Book> calBooks = new ArrayList<>();
        for(int i = (page-1) * 10; i < page*10; i++) {

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

    @GetMapping("/search/book/{isbn}")
    public String bookDetail(@PathVariable("isbn") String isbn, Model model, HttpServletRequest request) {

        boolean isMarked;

        //책 클릭시 책에 대한 정보 찾아서 모델에 넘기기.
        Book book= bookService.findBookByIsbn(isbn);

        //책 클릭시 책의 리뷰에 대한 정보 찾아서 모델에 넘기기
//        List<Review> reviews = book.getReviews();
//        List<Review> reviews = new ArrayList<>(book.getReviews());
        List<Review> reviews = reviewService.getReviewsByBook(book);

        //책 클릭시 즐겨찾기 여부에 대한 정보를 찾아야한다.

        HttpSession session = request.getSession(false);

        //계속 null오류가 나길래 왜인가 봤더니 .session.getAttribute("user)
        //세션 자체가 null이기 때문에 속성에 접근할 수 없었다. 세션이 null이라는말은 로그인이 되어 있지 않을때이다.
        //그래서 session == null을 추가.
        if(session == null || session.getAttribute("user") == null) {
            isMarked = false;
        } else {
            Member member = (Member) request.getSession(false).getAttribute("user");
            if(bookMarkService.existsByMemberAndBook(book, member)) {
                isMarked = true;
            } else {
                isMarked = false;
            }
            //로그인한 사용자에 대해 수정, 삭제 버튼을 만들어낼 수 있도록.
            model.addAttribute("user", member);

        }

        //자기가 작성한 리뷰에 대해 리뷰 수정 삭제버튼이 가능하도록 해야한다.

        model.addAttribute("isMarked", isMarked);
        model.addAttribute("reviews", reviews);
        model.addAttribute("book", book);


        return "search/book";
    }

    @PostMapping("/search/book/review/add/{isbn}")
    public String addReview(@PathVariable(name = "isbn") String isbn, @RequestParam("review")String review,
                            HttpServletRequest request, RedirectAttributes redirectAttributes) {

        Book book = bookService.findBookByIsbn(isbn);
        Member member = (Member) request.getSession(false).getAttribute("user");

        reviewService.save(book, member, review);


//        리다이렉트할때 경로에 변수 있으면 이렇게 addAttrivute로 넘겨주어야하는듯
        // 리다이렉트와 포워드 차이를 공부하자.

        redirectAttributes.addAttribute("isbn", isbn);

        return  "redirect:/search/book/{isbn}";
    }

    @DeleteMapping("/search/book/review/delete/{reviewIndex}")
    public ResponseEntity<String> deleteReview(@PathVariable("reviewIndex") Long reviewIndex) {

        try {
            reviewService.deleteReview(reviewIndex);
            return ResponseEntity.ok("리뷰가 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류가 발생하였습니다.");
        }

    }


    @GetMapping("/book/bookmark/add/{isbn}")
    public String addBookmark(@PathVariable("isbn") String isbn, HttpServletRequest request,
                           RedirectAttributes redirectAttributes) {

        //즐겨찾기 저장해주기.
        //이렇게 안전하게 세션을 가져와도 null오류가 나지 않는 이유는 필터를 적용하였기 때문에
        //필터가 먼저 작동하여 로그인이 되어 있지않으면 로그인 화면으로 이동하게 해준다.

        Member member = (Member) request.getSession(false).getAttribute("user");
        Book book = bookService.findBookByIsbn(isbn);

//        리스트를 이렇게 가져오는 것중에 어떤것이 나은 방법일까나?
        List<Review> reviews = book.getReviews();
//        List<Review> reviews = new ArrayList<>(book.getReviews());
//        List<Review> reviews = reviewService.getReviewsByBook(book);


        bookMarkService.save(book, member);

//      redirectAttributes로 보내면 파라미터로 보내는구나? reviews가 리스트이기 떄문에
//        리스트를 String으로 변환하는데 문제가 있다는 것이로구나?
//        redirectAttributes.addAttribute("reviews", reviews);
//        redirectAttributes.addAttribute("book", book);
        redirectAttributes.addAttribute("isbn", isbn);

        return  "redirect:/search/book/{isbn}";
    }

    @GetMapping("/book/bookmark/remove/{isbn}")
    public String removeBookmark(@PathVariable("isbn")String isbn, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        Member member = (Member)request.getSession(false).getAttribute("user");
        Book book = bookService.findBookByIsbn(isbn);


        List<Review> reviews = book.getReviews();
//        List<Review> reviews = new ArrayList<>(book.getReviews());
//        List<Review> reviews = reviewService.getReviewsByBook(book);


        bookMarkService.removeBookmark(book, member);

        redirectAttributes.addAttribute("isbn", isbn);

        return  "redirect:/search/book/{isbn}";
    }

}

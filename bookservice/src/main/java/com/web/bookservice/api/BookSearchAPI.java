package com.web.bookservice.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.bookservice.domain.Book;
import com.web.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Component
public class BookSearchAPI {

    private final BookService bookService;

    public List<Book> func(String query) {
        String clientId = "ha7STvEFwLDwnDflONB5"; //애플리케이션 클라이언트 아이디
        String clientSecret = "4zNLWo0tMF"; //애플리케이션 클라이언트 시크릿

//        List<BookParsing> books = new ArrayList<>();
//        BookParsing bookParsing = new BookParsing();
        List<Book> books = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;

        String text = null;
        try {
            text = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/book?query=" + text
                + "&display=5";    // JSON 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

        try {
            jsonNode = objectMapper.readTree(responseBody);

            for(JsonNode itemNode : jsonNode.get("items")) {

                Book book = new Book();
                log.info("title={}", itemNode.get("pubdate").asText());
                book.setTitle(itemNode.get("title").asText());
                book.setAuthor(itemNode.get("author").asText());
                book.setDescription(itemNode.get("description").asText());
                book.setPublisher(itemNode.get("publisher").asText());
                book.setImage(itemNode.get("image").asText());
                book.setIsbn(itemNode.get("isbn").asText());

                //20221512를 2022-15-12로 포맷
                LocalDate date = LocalDate.parse(itemNode.get("pubdate").asText(), DateTimeFormatter.ofPattern("yyyyMMdd"));

                book.setPubdate(date);


                books.add(book);
                bookService.save(book);
            }


        } catch (Exception e) {

        }
        return books;
    }


    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
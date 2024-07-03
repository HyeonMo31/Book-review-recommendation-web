package com.web.bookservice.controller.api;

import com.web.bookservice.domain.Discussion;
import com.web.bookservice.dto.DiscussionListDTO;
import com.web.bookservice.dto.SearchResponse;
import com.web.bookservice.service.DiscussionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DiscussionApiController {

    private final DiscussionService discussionService;

//    @GetMapping("/api/discussions")
//    public ResponseEntity<SearchResponse> getAllDiscussion(
//            @RequestParam(name = "page", defaultValue = "0")int page,
//            @RequestParam(name = "select", required = false)String select,
//            @RequestParam(name = "query", required = false)String query) {
//
//        Page<Discussion> pageResult = discussionService.getDataByPage(select, query, page, 10);
//
//        long start = pageResult.getPageable().getOffset()+1;
//        long end = (start - 1) + pageResult.getNumberOfElements();
//
//        SearchResponse searchResponse = new SearchResponse(start, end, pageResult.getTotalPages(),
//                pageResult.getTotalElements(), page);
//        List<DiscussionListDTO> discussionList = new ArrayList<>();
//
//        for(Discussion discussion : pageResult) {
//
//            DiscussionListDTO discussionListDTO = new DiscussionListDTO();
//            discussionListDTO.setId(discussion.getId());
//            discussionListDTO.setWriter(discussion.getMember().getName());
//            discussionListDTO.setTitle(discussion.getTitle());
//            discussionListDTO.setBookTitle(discussion.getBook().getTitle());
//            discussionListDTO.setWriteDate(discussion.getWriteDate());
//
//            discussionList.add(discussionListDTO);
//        }
//
//        searchResponse.setDiscussionListDTOS(discussionList);
//        return ResponseEntity.ok(searchResponse);
//    }

}

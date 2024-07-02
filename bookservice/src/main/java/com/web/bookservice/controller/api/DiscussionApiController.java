package com.web.bookservice.controller.api;

import com.web.bookservice.domain.Discussion;
import com.web.bookservice.dto.DiscussionSearchDTO;
import com.web.bookservice.service.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DiscussionApiController {

    private final DiscussionService discussionService;

    @GetMapping("/api/discussions")
    public ResponseEntity<List<DiscussionSearchDTO>> getAllDiscussion(@RequestParam(name= "page", defaultValue = "0")int page,
                                                                      @RequestParam(name = "select", required = false) String select,
                                                                      @RequestParam(name = "query", required = false) String query)  {

        Page<Discussion> pageResult = discussionService.getDataByPage(select, query, page, 10);;

        List<DiscussionSearchDTO> discussionList = new ArrayList<>();

        for(Discussion discussion : pageResult) {

            DiscussionSearchDTO discussionSearchDTO = new DiscussionSearchDTO();
            discussionSearchDTO.setId(discussion.getId());
            discussionSearchDTO.setWriter(discussion.getMember().getName());
            discussionSearchDTO.setTitle(discussion.getTitle());
            discussionSearchDTO.setBookTitle(discussion.getBook().getTitle());
            discussionSearchDTO.setWriteDate(discussion.getWriteDate());

            discussionList.add(discussionSearchDTO);
        }

        return ResponseEntity.ok(discussionList);
    }

}

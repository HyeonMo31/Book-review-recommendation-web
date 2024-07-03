package com.web.bookservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchResponse {

    private SearchDTO searchDTO;

    public SearchResponse(long start, long end, long pageCount, long totalSize, long currentPage) {
        searchDTO = new SearchDTO(start, end, pageCount, totalSize, currentPage);
    }

    private List<DiscussionListDTO> discussionListDTOS;

}

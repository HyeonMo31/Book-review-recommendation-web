package com.web.bookservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDTO {

    private long start;
    private long end;
    private long pageCount;
    private long totalSize;
    private long currentPage;
    private String select;
    private String query;

    public SearchDTO() {}
    public SearchDTO(long start, long end, long pageCount, long totalSize, long currentPage) {
        this.start = start;
        this.end = end;
        this.pageCount = pageCount;
        this.totalSize = totalSize;
        this.currentPage = currentPage;
    }
}
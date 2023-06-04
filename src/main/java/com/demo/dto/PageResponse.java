package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageResponse {

    private int recordCount;
    private List<?> response;
//    private int pageNumber;
//    private int pageSize;
//    private int totalPages;
//    private long totalElements;

    LinkedHashMap<String,Integer> metaData;
}

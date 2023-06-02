package com.demo.dto;

import com.demo.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestaurantResponse {

    private int recordCount;
    private List<Restaurant> response;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
}

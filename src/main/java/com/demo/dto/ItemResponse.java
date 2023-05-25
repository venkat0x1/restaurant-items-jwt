package com.demo.dto;

import com.demo.entity.Restaurant;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemResponse {
    private String restaurantName;
    private String address;
    private String itemName;
    private double price;
    private String description;

}

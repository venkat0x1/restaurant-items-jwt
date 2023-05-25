package com.demo.dto;

import com.demo.entity.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "items")
public class ItemDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "restaurant_id")
    private int restaurantId;

    @Column
    private String name;

    @Column
    private double price;

    @Column
    private String description;

}


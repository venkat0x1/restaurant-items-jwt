package com.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String mobile;

    @Column
    private String email;

    @Column
    private String website;

    @Column(length = 20)
    private String specialty;

    @Column
    private double rating;

    @Column
    private int x;

    @Column
    private int y;

}


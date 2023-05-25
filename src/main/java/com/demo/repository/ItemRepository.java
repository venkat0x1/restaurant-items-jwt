package com.demo.repository;

//import com.demo.dto.ItemResponse;
import com.demo.dto.ItemResponse;
import com.demo.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

//    @Query("SELECT i FROM Item i WHERE i.name LIKE CONCAT(:itemName, '%')")
//    Page<Item> getItemsByName(@Param("itemName") String itemName, Pageable pageable);

//    @Query(value = "SELECT r.name,r.address,i.name,i.price,i.description FROM items i JOIN restaurants r ON i.restaurant_id = r.id WHERE i.id IN (SELECT id FROM items WHERE name LIKE CONCAT(:itemName, '%'))", nativeQuery = true)
//    Page<ItemResponse> getItemsByName(@Param("itemName") String itemName, Pageable pageable);

    @Query("SELECT new com.demo.dto.ItemResponse(r.name, r.address, i.name, i.price, i.description) FROM Item i JOIN i.restaurant r WHERE i.name LIKE CONCAT(:itemName, '%')")
    Page<ItemResponse> getItemsByName(@Param("itemName") String itemName, Pageable pageable);


    @Query("SELECT new com.demo.dto.ItemResponse(r.name, r.address, i.name, i.price, i.description) FROM Item i JOIN i.restaurant r WHERE i.restaurant.name = :restaurantName")
    Page<ItemResponse> getItemsByRestaurantName(String restaurantName,Pageable pageable);

}

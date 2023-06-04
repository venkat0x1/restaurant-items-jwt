package com.demo.repository;

import com.demo.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT r FROM Restaurant r WHERE r.id IN (SELECT i.restaurant.id FROM Item i WHERE i.name LIKE CONCAT(:itemName, '%'))")
    List<Restaurant> getRestaurantsByItemName(String itemName);

    @Query(value = "SELECT *, SQRT(POWER((x - :x), 2) + POWER((y - :y), 2)) as distance FROM restaurants ORDER BY distance ASC", nativeQuery = true)
    List<Restaurant> getAllRestaurantsOrderByDistance(int x, int y);

    @Query(value = "SELECT *, SQRT(POWER((x - :x), 2) + POWER((y - :y), 2)) as distance FROM restaurants r WHERE r.id in (select id from items i where i.name like CONCAT(:itemName, '%') and i.price BETWEEN :lowPrice and :highPrice)  ORDER BY distance ASC",nativeQuery = true)
    List<Restaurant> getNearByRestaurantsByItemNameAndPriceRange(int x,int y,String itemName,double lowPrice,double highPrice);

    List<Restaurant> getRestaurantsByName(String name);

}

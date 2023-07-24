package com.demo.controller;

import com.demo.dto.PageResponse;
import com.demo.entity.Restaurant;
import com.demo.service.RestaurantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Restaurant")
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<PageResponse> getAllRestaurants(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10")  int pageSize, @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "ASC") String sortDirection) {
        return restaurantService.getAllRestaurants(pageNumber,pageSize,sortBy,sortDirection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable int id){
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Restaurant>> getRestaurantsByName(@RequestParam String name){
        return restaurantService.getRestaurantsByName(name);
    }

    @GetMapping("/near-by")
    public List<Restaurant> getAllRestaurantsOrderByDistance(@RequestParam(defaultValue = "0") int x, @RequestParam(defaultValue = "0") int y){
        return restaurantService.getAllRestaurantsOrderByDistance(x,y);
    }

    @GetMapping("/items")
    public ResponseEntity<List<Restaurant>> getRestaurantsByItem(@RequestParam String itemName) {
        return restaurantService.getRestaurantsByItem(itemName);
    }

    @GetMapping("/items/price")
    public ResponseEntity<List<Restaurant>> getRestaurantsByItemAndPrice(@RequestParam String itemName, @RequestParam(defaultValue = "100") double lowPrice, @RequestParam(defaultValue = "500") double highPrice, @RequestParam(defaultValue ="0") int x, @RequestParam(defaultValue="0") int y) {
        return restaurantService.getNearbyRestaurantsByItemAndPriceRange(itemName, lowPrice, highPrice,x,y);
    }

    @PostMapping
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantService.restaurantAdding(restaurant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRestaurantById(@PathVariable int id){
        return restaurantService.deleteRestaurantById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRestaurantById(@PathVariable int id,@RequestBody Restaurant restaurant){
        return restaurantService.updateRestaurantById(id,restaurant);
    }

}

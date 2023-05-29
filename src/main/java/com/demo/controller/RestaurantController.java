package com.demo.controller;

import com.demo.entity.Restaurant;
import com.demo.entity.User;
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
    public Page<Restaurant> getAllRestaurants(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10")  int pageSize, @RequestParam(defaultValue = "name") String sortBy,@RequestParam(defaultValue = "ASC") String orderDirection) {
        return restaurantService.getAllRestaurants(offset,pageSize,sortBy,orderDirection);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable int id){
        return restaurantService.getRestaurantById(id);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Page<Restaurant>> getRestaurantsByName(@PathVariable String name,@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10")  int pageSize, @RequestParam(defaultValue = "name") String sortBy,@RequestParam(defaultValue = "ASC") String orderDirection){

        return restaurantService.getRestaurantsByName(name,offset,pageSize,sortBy,orderDirection);
    }

    @GetMapping("/near-by")
    public Page<Restaurant> getAllRestaurantsOrderByDistance(@RequestParam(defaultValue = "0") int x, @RequestParam(defaultValue = "0") int y,@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10")  int pageSize, @RequestParam(defaultValue = "name") String sortBy,@RequestParam(defaultValue = "ASC") String orderDirection){
        return restaurantService.getAllRestaurantsOrderByDistance(x,y,offset,pageSize,sortBy,orderDirection);
    }

    @GetMapping("/items/{itemName}")
    public ResponseEntity<Page<Restaurant>> getRestaurantsByItem(@PathVariable String itemName,@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10")  int pageSize, @RequestParam(defaultValue = "name") String sortBy,@RequestParam(defaultValue = "ASC") String orderDirection) {
        return restaurantService.getRestaurantsByItem(itemName,offset,pageSize,sortBy,orderDirection);
    }


    @GetMapping("/items/{itemName}/price")
    public ResponseEntity<Page<Restaurant>> getRestaurantsByItemAndPrice(@PathVariable String itemName, @RequestParam(defaultValue = "100") double lowPrice, @RequestParam(defaultValue = "500") double highPrice, @RequestParam(defaultValue ="0") int x, @RequestParam(defaultValue="0") int y,@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10")  int pageSize, @RequestParam(defaultValue = "name") String sortBy,@RequestParam(defaultValue = "ASC") String orderDirection) {
        return restaurantService.getNearbyRestaurantsByItemAndPriceRange(itemName, lowPrice, highPrice,x,y,offset,pageSize,sortBy,orderDirection);
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

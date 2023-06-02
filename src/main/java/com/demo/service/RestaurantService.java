package com.demo.service;

import com.demo.dto.RestaurantResponse;
import com.demo.entity.Restaurant;
import com.demo.exception.ArgumentsMismatchException;
import com.demo.exception.ResourceNotFoundException;
import com.demo.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;


    public static int getMaxValue(int value){
        return Math.max(value,0);
    }

    public static Sort.Direction getSortDirection(String orderDirection) {
        return orderDirection.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    public static Pageable getPageable(int pageNumber, int pageSize, String sortBy, String orderDirection){
        return PageRequest.of(getMaxValue(pageNumber),getMaxValue(pageSize),getSortDirection(orderDirection), sortBy);
    }


    public ResponseEntity<RestaurantResponse> getAllRestaurants(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Page<Restaurant> restaurantPage= restaurantRepository.findAll(getPageable(pageNumber, pageSize, sortBy, sortDirection));
        RestaurantResponse restaurantResponse=new RestaurantResponse(restaurantPage.getSize(),restaurantPage.getContent(),pageNumber,pageSize,restaurantPage.getTotalPages(),restaurantPage.getTotalElements());
        return ResponseEntity.ok(restaurantResponse);
    }

    public ResponseEntity<Restaurant> getRestaurantById(int id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        return ResponseEntity.ok(restaurant);
    }

    public ResponseEntity<Page<Restaurant>> getRestaurantsByItem(String itemName, int offset, int pageSize, String sortBy, String orderDirection) {
        Page<Restaurant> restaurantList = restaurantRepository.getRestaurantsByItemName(itemName, getPageable(offset,pageSize,sortBy,orderDirection));
        if (restaurantList.isEmpty()) {
            throw new ResourceNotFoundException("No restaurants found with the given item: " + itemName);
        }
        return ResponseEntity.ok(restaurantList);
    }

    public Page<Restaurant> getAllRestaurantsOrderByDistance(int x, int y, int offset, int pageSize, String sortBy, String orderDirection) {
        Pageable pageable = PageRequest.of(getMaxValue(offset),getMaxValue(pageSize), getSortDirection(orderDirection), sortBy);
        return restaurantRepository.getAllRestaurantsOrderByDistance(getMaxValue(x), getMaxValue(y), pageable);
    }

    public ResponseEntity<Page<Restaurant>> getNearbyRestaurantsByItemAndPriceRange(String itemName, double lowPrice, double highPrice, int x, int y, int offset, int pageSize, String sortBy, String orderDirection) {
        Page<Restaurant> restaurantList = restaurantRepository.getNearByRestaurantsByItemNameAndPriceRange(getMaxValue(x),getMaxValue(y), itemName, lowPrice, highPrice, getPageable(offset,pageSize,sortBy,orderDirection));
        if (restaurantList.isEmpty()) {
            throw new ResourceNotFoundException("No restaurants found with the given item name and price range.");
        }
        return ResponseEntity.ok(restaurantList);
    }

    public ResponseEntity<Restaurant> restaurantAdding(Restaurant restaurant) {
        try {
            return ResponseEntity.ok(restaurantRepository.save(restaurant));
        } catch (Exception ex) {
            throw new ArgumentsMismatchException("Invalid restaurant details");
        }
    }

    public ResponseEntity<Page<Restaurant>> getRestaurantsByName(String name, int offset, int pageSize, String sortBy, String orderDirection) {
        Page<Restaurant> restaurantList = restaurantRepository.getRestaurantsByName(name, getPageable(offset,pageSize,sortBy,orderDirection));
        if (restaurantList.isEmpty()) {
            throw new ResourceNotFoundException("No restaurants found with the given name: " + name);
        }
        return ResponseEntity.ok(restaurantList);
    }

    public ResponseEntity<String> deleteRestaurantById(int id) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isEmpty()) {
            throw new ResourceNotFoundException("Restaurant Not Found With Id:"+id);
        } else {
            restaurantRepository.deleteById(id);
            return ResponseEntity.ok("Restaurant Removed..!");
        }
    }
    public ResponseEntity<String> updateRestaurantById(int id, Restaurant updateRestaurant) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));

        restaurant.setName(Objects.requireNonNullElse(updateRestaurant.getName(), restaurant.getName()));
        restaurant.setMobile(Objects.requireNonNullElse(updateRestaurant.getMobile(), restaurant.getMobile()));
        restaurant.setAddress(Objects.requireNonNullElse(updateRestaurant.getAddress(), restaurant.getAddress()));
        restaurant.setEmail(Objects.requireNonNullElse(updateRestaurant.getEmail(), restaurant.getEmail()));
        restaurant.setRating(Objects.requireNonNullElse(updateRestaurant.getRating(), restaurant.getRating()));
        restaurant.setSpecialty(Objects.requireNonNullElse(updateRestaurant.getSpecialty(), restaurant.getSpecialty()));
        restaurant.setWebsite(Objects.requireNonNullElse(updateRestaurant.getWebsite(), restaurant.getWebsite()));
        restaurant.setX(Objects.requireNonNullElse(updateRestaurant.getX(), restaurant.getX()));
        restaurant.setY(Objects.requireNonNullElse(updateRestaurant.getY(), restaurant.getY()));

        try {
            restaurantRepository.save(restaurant);
            return ResponseEntity.ok("Restaurant information updated.");
        } catch (Exception ex) {
            throw new ArgumentsMismatchException("Invalid restaurant details for update.");
        }
    }

}

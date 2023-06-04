package com.demo.service;

import com.demo.dto.PageResponse;
import com.demo.entity.Restaurant;
import com.demo.exception.InvalidInputException;
import com.demo.exception.ResourceNotFoundException;
import com.demo.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public static Pageable getPageable(int pageNumber, int pageSize, String sortBy, String sortDirection){
        return PageRequest.of(getMaxValue(pageNumber),getMaxValue(pageSize),getSortDirection(sortDirection), sortBy);
    }

    public ResponseEntity<PageResponse> getAllRestaurants(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Page<Restaurant> restaurantPage= restaurantRepository.findAll(getPageable(pageNumber, pageSize, sortBy, sortDirection));
        return getPageResponse(restaurantPage);
    }

    public static ResponseEntity<PageResponse> getPageResponse(Page<?> page){
        if (page.isEmpty()){
            throw new ResourceNotFoundException("Not found any data");
        }
        PageResponse pageResponse =new PageResponse();
        pageResponse.setRecordCount(page.getNumberOfElements());
        pageResponse.setResponse(page.getContent());
        LinkedHashMap<String, Integer> metaData = new LinkedHashMap<>();
        metaData.put("pageNumber", page.getNumber());
        metaData.put("pageSize", page.getSize());
        metaData.put("totalPages", page.getTotalPages());
        metaData.put("totalElements", (int) page.getTotalElements());
        pageResponse.setMetaData(metaData);
        return ResponseEntity.ok(pageResponse);
    }

    public ResponseEntity<Restaurant> getRestaurantById(int id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        return ResponseEntity.ok(restaurant);
    }

    public ResponseEntity<List<Restaurant>> getRestaurantsByItem(String itemName) {
        List<Restaurant> restaurantList = restaurantRepository.getRestaurantsByItemName(itemName);
        if (restaurantList.isEmpty()) {
            throw new ResourceNotFoundException("No restaurants found with the given item: " + itemName);
        }
        return ResponseEntity.ok(restaurantList);
    }

    public List<Restaurant> getAllRestaurantsOrderByDistance(int x, int y) {
        return restaurantRepository.getAllRestaurantsOrderByDistance(getMaxValue(x), getMaxValue(y));
    }

    public ResponseEntity<List<Restaurant>> getNearbyRestaurantsByItemAndPriceRange(String itemName, double lowPrice, double highPrice, int x, int y) {
        List<Restaurant> restaurantList = restaurantRepository.getNearByRestaurantsByItemNameAndPriceRange(getMaxValue(x),getMaxValue(y), itemName, lowPrice, highPrice);
        if (restaurantList.isEmpty()) {
            throw new ResourceNotFoundException("No restaurants found with the given item name and price range.");
        }
        return ResponseEntity.ok(restaurantList);
    }

    public ResponseEntity<Restaurant> restaurantAdding(Restaurant restaurant) {
        try {
            return ResponseEntity.ok(restaurantRepository.save(restaurant));
        } catch (Exception ex) {
            throw new InvalidInputException("Invalid restaurant details");
        }
    }

    public ResponseEntity<List<Restaurant>> getRestaurantsByName(String name) {
        List<Restaurant> restaurantList = restaurantRepository.getRestaurantsByName(name);
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
            throw new InvalidInputException("Invalid restaurant details for update.");
        }
    }

}

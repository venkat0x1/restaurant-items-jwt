package com.demo.controller;

import com.demo.dto.ItemDto;
import com.demo.dto.ItemResponse;
import com.demo.dto.PageResponse;
import com.demo.entity.Item;
import com.demo.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Item")
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Operation(summary = "Retrieve all items")
    @GetMapping
    public ResponseEntity<PageResponse> getAllItems(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortByName, @RequestParam(defaultValue = "ASC") String sortDirection) {
        return itemService.getAllItems(pageNumber, pageSize, sortByName, sortDirection);
    }


    @Operation(summary = "Retrieve item by id")
    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable int id) {
        return itemService.getItemById(id);
    }

    @GetMapping("/name")
    public ResponseEntity<PageResponse> getItemByItemName(@RequestParam String itemName, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(name = "sortBy", defaultValue = "id") String sortBy, @RequestParam(name = "direction", defaultValue = "ASC") String sortDirection) {
        return itemService.getItemByItemName(itemName, pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<ItemResponse>> getItemsByRestaurantName(@RequestParam String restaurantName) {
        return itemService.getItemsByRestaurantName(restaurantName);
    }

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@RequestBody ItemDto itemDto) {
        return itemService.itemAdding(itemDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateItemById(@PathVariable int id, @RequestBody ItemDto item) {
        return itemService.updateItemById(id, item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItemById(@PathVariable int id) {
        return itemService.deleteItemById(id);
    }
}

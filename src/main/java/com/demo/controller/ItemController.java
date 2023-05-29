package com.demo.controller;

import com.demo.dto.ItemDto;
import com.demo.dto.ItemResponse;
import com.demo.entity.Item;
import com.demo.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Item")
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Operation(summary = "Retrieve all items")
    @GetMapping
    public Page<Item> getAllItems(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortByName, @RequestParam(defaultValue = "ASC") String sortDirection) {
        return itemService.getAllItems(offset, pageSize, sortByName, sortDirection);
    }


    @Operation(summary = "Retrieve item by id")
    @GetMapping("/{id}")
    public  ResponseEntity<ItemDto> getItemById(@PathVariable int id){
        return itemService.getItemById(id);
    }
    @GetMapping("/name/{itemName}")
    public ResponseEntity<Page<ItemResponse>> getItemsByItemName(@PathVariable String itemName, @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int size, @RequestParam(name = "sortBy", defaultValue = "id") String sortBy, @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
        return itemService.getItemsByItemName(itemName, offset, size, sortBy, direction);
    }

    @GetMapping("/restaurants/{restaurantName}")
    public ResponseEntity<Page<ItemResponse>> getItemsByRestaurantName(@PathVariable String name, @RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "ASC") String sortDirection) {
        return itemService.getItemsByRestaurantName(name, offset, pageSize, sortBy, sortDirection);
    }

    @PostMapping
    public ResponseEntity<String> addItem(@RequestBody ItemDto itemDto) {
        return itemService.itemAdding(itemDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateItemById(@PathVariable int id, @RequestBody Item item) {
        return itemService.updateItemById(id, item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItemById(@PathVariable int id) {
        return itemService.deleteItemById(id);
    }
}

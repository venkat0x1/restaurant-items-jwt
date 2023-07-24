package com.demo.service;

import com.demo.dto.ItemDto;
import com.demo.dto.ItemResponse;
import com.demo.dto.PageResponse;
import com.demo.entity.Item;
import com.demo.exception.InvalidInputException;
import com.demo.exception.ResourceNotFoundException;
import com.demo.repository.ItemDtoRepository;
import com.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemDtoRepository itemDtoRepository;

    public ResponseEntity<PageResponse> getAllItems(int pageNumber, int pageSize, String sortBy, String orderDirection) {
        Page<Item> itemPage=itemRepository.findAll(RestaurantService.getPageable(pageNumber,pageSize,sortBy,orderDirection));
        return RestaurantService.getPageResponse(itemPage);
    }

    public ResponseEntity<ItemDto> getItemById(int id){
        ItemDto item = itemDtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
        return ResponseEntity.ok(item);
    }

    public ResponseEntity<PageResponse> getItemByItemName(String itemName, int pagesize, int pageSize, String sortBy, String sortDirection) {
        Page<ItemResponse> itemPage = itemRepository.getItemsByName(itemName, RestaurantService.getPageable(pagesize,pageSize,sortBy,sortDirection));
        return  RestaurantService.getPageResponse(itemPage);

        }


    public ResponseEntity<List<ItemResponse>> getItemsByRestaurantName(String name) {
        List<ItemResponse> itemList = itemRepository.getItemsByRestaurantName(name);
        if (itemList.isEmpty()) {
            throw new ResourceNotFoundException("No items Found with restaurant name : "+name);
        } else {
            return ResponseEntity.ok(itemList);
        }
    }

    public ResponseEntity<ItemDto> itemAdding(ItemDto item) {
        try {
            return ResponseEntity.ok(itemDtoRepository.save(item));
        } catch (Exception ex) {
            throw new InvalidInputException("Invalid input");
        }
    }

    public ResponseEntity<String> updateItemById(int id, ItemDto updateItem) {
            ItemDto item = itemDtoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
        try {
            item.setName(updateItem.getName() != null ? updateItem.getName() : item.getName());
            item.setPrice(Objects.isNull(updateItem.getPrice()) ? item.getPrice() : updateItem.getPrice());
            item.setRestaurantId(Objects.isNull(updateItem.getRestaurantId()) ? item.getRestaurantId() : updateItem.getRestaurantId() );
            item.setDescription(updateItem.getDescription() != null ? updateItem.getDescription() : item.getDescription());
            itemDtoRepository.save(item);
            return ResponseEntity.ok("Item Updated!");
        } catch (Exception e){
            throw new InvalidInputException("Invalid Input..!");
        }
    }

    public ResponseEntity<String> deleteItemById(int id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty()) {
            throw new ResourceNotFoundException("Item not found with id: "+id);
        } else {
            itemRepository.deleteById(id);
            return ResponseEntity.ok("Item deleted");
        }
    }
}

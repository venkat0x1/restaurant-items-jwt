package com.demo.service;

import com.demo.dto.ItemDto;
import com.demo.dto.ItemResponse;
import com.demo.entity.Item;
import com.demo.exception.ArgumentsMismatchException;
import com.demo.exception.ResourceNotFoundException;
import com.demo.repository.ItemDtoRepository;
import com.demo.repository.ItemRepository;
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
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemDtoRepository itemDtoRepository;

    public Page<Item> getAllItems(int offset, int pageSize, String sortBy, String orderDirection) {
        return itemRepository.findAll(RestaurantService.getPageable(offset,pageSize,sortBy,orderDirection));
    }

    public ResponseEntity<ItemDto> getItemById(int id){
        ItemDto item = itemDtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
        return ResponseEntity.ok(item);
    }

    public ResponseEntity<Page<ItemResponse>> getItemsByItemName(String itemName, int offset, int pageSize, String sortBy, String orderDirection) {
        Page<ItemResponse> itemPage = itemRepository.getItemsByName(itemName, RestaurantService.getPageable(offset,pageSize,sortBy,orderDirection));
        if (itemPage.isEmpty()) {
            throw new ResourceNotFoundException("No items found with the given item name : "+itemName);
        } else {
            return ResponseEntity.ok(itemPage);
        }
    }


    public ResponseEntity<Page<ItemResponse>> getItemsByRestaurantName(String name, int offset, int pageSize, String sortBy, String orderDirection) {
        Page<ItemResponse> itemList = itemRepository.getItemsByRestaurantName(name, RestaurantService.getPageable(offset,pageSize,sortBy,orderDirection));
        if (itemList.isEmpty()) {
            throw new ResourceNotFoundException("No items Found with restaurant name : "+name);
        } else {
            return ResponseEntity.ok(itemList);
        }
    }

    public ResponseEntity<String> itemAdding(ItemDto item) {
        try {
            itemDtoRepository.save(item);
            return ResponseEntity.ok("Item Added..!");
        } catch (Exception ex) {
            throw new ArgumentsMismatchException("Invalid input");
        }
    }

    public ResponseEntity<String> updateItemById(int id, Item updateItem) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));

        item.setName(updateItem.getName() != null ? updateItem.getName() : item.getName());
        item.setPrice(Objects.isNull(updateItem.getPrice()) ? item.getPrice() : updateItem.getPrice());
        item.setRestaurant(updateItem.getRestaurant() != null ? updateItem.getRestaurant() : item.getRestaurant());
        item.setDescription(updateItem.getDescription() != null ? updateItem.getDescription() : item.getDescription());
        itemRepository.save(item);

        return ResponseEntity.ok("Item Updated!");
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

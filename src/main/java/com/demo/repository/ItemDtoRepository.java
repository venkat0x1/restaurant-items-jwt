package com.demo.repository;

import com.demo.dto.ItemDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDtoRepository extends JpaRepository<ItemDto,Integer> {
}

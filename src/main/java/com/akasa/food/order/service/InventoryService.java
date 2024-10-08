package com.akasa.food.order.service;

import com.akasa.food.order.dto.*;
import com.akasa.food.order.models.Category;
import com.akasa.food.order.models.Inventory;
import com.akasa.food.order.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Response getAll() {
        log.info("Get request for fetch Inventories by user :: {}", MDC.get("user"));
        List<Inventory> inventories = inventoryRepository.findAll();
        if(inventories.isEmpty()){
            log.info("Inventories not found in the DB");
            return ErrorResponse.builder()
                    .status("500")
                    .error("Empty data")
                    .message("Please try after some time")
                    .build();
        }
        log.info("Inventories list :: {} user :: {}", inventories , MDC.get("user"));
        return AllInventoryResponse.builder()
                .status("200")
                .message("Success")
                .inventories(inventories)
                .build();
    }

    public Response getInventory(Long id) {
        log.info("Get request for fetch Inventories by user :: {}, item :: {}", MDC.get("user"), id);
        Inventory inventories = inventoryRepository.findByItemId(id);
        if(inventories == null || inventories.getStock() == 0){
            log.info("Inventories not found in the DB");
            return ErrorResponse.builder()
                    .status("500")
                    .error("Empty data")
                    .message("Stock not available")
                    .build();
        }
        log.info("Inventories list :: {} user :: {}", inventories , MDC.get("user"));
        return InventoryResponse.builder()
                .status("200")
                .message("Success")
                .item(inventories)
                .build();
    }
}

package com.pivotree.appzone.controller;

import com.pivotree.appzone.entity.Customer;
import com.pivotree.appzone.enums.UserType;
import com.pivotree.appzone.exception.ResponseMessage;
import com.pivotree.appzone.intemplate.InventoryValidation;
import com.pivotree.appzone.intemplate.UsernameInput;
import com.pivotree.appzone.service.CustomerService;
import com.pivotree.appzone.service.InventoryService;
import com.pivotree.appzone.entity.Inventory;
import com.pivotree.appzone.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {


    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private CustomerService customerService;

    /**
     * Creates a new inventory record in the database.
     *
     * @param inventoryData the inventory data to be created
     * @return a response indicating whether the creation was successful
     */

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody InventoryValidation inventoryData) {

        List<Inventory> acceptedInventories = inventoryService.create(inventoryData);
        return new ResponseEntity<>(new ResponseMessage(acceptedInventories), HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<Page<Inventory>> getAllInventory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Inventory> inventoryPage = inventoryService.getAllInventory(pageable);
        return new ResponseEntity<>(inventoryPage, HttpStatus.OK);
    }

    }


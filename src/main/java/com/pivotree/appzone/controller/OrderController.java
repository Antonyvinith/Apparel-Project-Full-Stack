package com.pivotree.appzone.controller;

import com.pivotree.appzone.entity.Customer;
import com.pivotree.appzone.entity.Inventory;
import com.pivotree.appzone.enums.UserType;
import com.pivotree.appzone.exception.ResponseMessage;
import com.pivotree.appzone.intemplate.OrderInput;
import com.pivotree.appzone.intemplate.UsernameInput;
import com.pivotree.appzone.outtemplate.OrderOutput;
import com.pivotree.appzone.service.CustomerService;
import com.pivotree.appzone.service.OrderService;
import com.pivotree.appzone.entity.Order;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.pivotree.appzone.intemplate.PaginationParams;
import com.pivotree.appzone.repository.OrderRepository;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerService customerService;

    /**
     * Creates a new order based on the given input.
     *
     * @param order the order details
     * @return the created order
     */

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/createOrder")
    public ResponseEntity<Object> createOrder(@RequestBody OrderInput order) {
        Order orderOutput = orderService.createOrder(order);
        OrderOutput orderOutput1 = new OrderOutput(orderOutput.getId(), orderOutput.getOrderReference(), orderOutput.getShippingAddress(), orderOutput.getStatus(), orderOutput.getOrderLines(), orderOutput.getOrderType(), orderOutput.getCustomer(), orderOutput.getBillingAddress(), orderOutput.getFulfillments(), orderOutput.getDeliveryType(), orderOutput.getOrderTotal());
        return new ResponseEntity<>(new ResponseMessage(orderOutput1), HttpStatusCode.valueOf(201));
    }



    @GetMapping("/getAll")
    public ResponseEntity<Page<Order>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderService.getAllOrders(pageable);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/getOrdersByCustomer")
    public ResponseEntity<Object> getOrdersForCustomer(@RequestBody PaginationParams template) {
        Page<Order> orders = orderService.getLatestOrdersForCustomer(template);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/CC")
    public ResponseEntity<Page<Order>> getCCOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Order> orders = orderService.getLatestOrdersByOrderType("CC", PageRequest.of(page, size));
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/HD")
    public ResponseEntity<Page<Order>> getHDOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Order> orders = orderService.getLatestOrdersByOrderType("HD", PageRequest.of(page, size));
        return ResponseEntity.ok(orders);
    }

}

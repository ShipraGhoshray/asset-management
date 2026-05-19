package com.selflearning.controller;

import com.selflearning.dto.CreateOrderRequest;
import com.selflearning.model.Order;
import com.selflearning.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody CreateOrderRequest request) {
        orderService.createOrder(request);
        return ResponseEntity.ok("Order Created!");
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/symbol/{symbol}")
    public List<Order> getOrdersBySymbol(@PathVariable String symbol) {
        return orderService.getOrdersBySymbol(symbol);
    }
}
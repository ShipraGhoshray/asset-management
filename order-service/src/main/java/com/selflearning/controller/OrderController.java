package com.selflearning.controller;

import com.selflearning.dto.CreateOrderRequest;
import com.selflearning.dto.OrderStatusResponse;
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
        String orderId = orderService.createOrder(request);
        return ResponseEntity.ok("Order Created: " + orderId);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/symbol/{symbol}")
    public List<Order> getOrdersBySymbol(@PathVariable String symbol) {
        return orderService.getOrdersBySymbol(symbol);
    }

    @GetMapping("/{orderId}/status")
    public OrderStatusResponse getOrderStatus(@PathVariable String orderId) {
        return orderService.getOrderStatusById(orderId);
    }
}
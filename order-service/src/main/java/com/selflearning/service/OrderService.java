package com.selflearning.service;

import com.selflearning.dto.CreateOrderRequest;
import com.selflearning.dto.OrderStatusResponse;
import com.selflearning.model.Order;

import java.util.List;

public interface OrderService {

    public String createOrder(CreateOrderRequest request);
    public List<Order> getAllOrders();
    public List<Order> getOrdersBySymbol(String symbol);
    public OrderStatusResponse getOrderStatusById(String id);
}
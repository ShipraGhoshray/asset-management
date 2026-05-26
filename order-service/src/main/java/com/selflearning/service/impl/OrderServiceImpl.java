package com.selflearning.service.impl;

import com.selflearning.dto.OrderStatusResponse;
import com.selflearning.mapper.OrderMapper;
import com.selflearning.messaging.OrderStatus;
import com.selflearning.messaging.events.OrderCreatedEvent;
import com.selflearning.model.Order;
import com.selflearning.dto.CreateOrderRequest;
import com.selflearning.repository.OrderRepository;
import com.selflearning.service.OrderService;
import com.selflearning.service.SagaOrchestrator;
import com.selflearning.util.IdGeneratorUtil;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;
    private final OrderRepository orderRepository;
    private final SagaOrchestrator orchestrator;

    @Override
    @Transactional
    public String createOrder(CreateOrderRequest request) {
        Order order = mapper.toEntity(request);
        order.setId(IdGeneratorUtil.orderId());
        order.setStatus(OrderStatus.NEW);
        Order savedOrder = orderRepository.save(order);
        OrderCreatedEvent event = new OrderCreatedEvent(
                String.valueOf(savedOrder.getId()),
                savedOrder.getSymbol(),
                savedOrder.getQuantity(),
                savedOrder.getPrice(),
                savedOrder.getSide(),
                savedOrder.getCreatedAt() == null ? LocalDate.now().toString() : savedOrder.getCreatedAt().toString()
        );
        orchestrator.handleOrderCreated(event);
        return savedOrder.getId();
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersBySymbol(String symbol) {
        return orderRepository.findBySymbol(symbol);
    }

    @Override
    public OrderStatusResponse getOrderStatusById(String id) {
        Optional<Order> order = Optional.ofNullable(orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found")));
        return order.map(value -> new OrderStatusResponse(
                        value.getId(), value.getStatus()))
                .orElse(null);
    }
}
package com.selflearning.service.impl;

import com.selflearning.mapper.OrderMapper;
import com.selflearning.messaging.events.OrderCreatedEvent;
import com.selflearning.model.Order;
import com.selflearning.domain.OrderStatus;
import com.selflearning.dto.CreateOrderRequest;
import com.selflearning.repository.OrderRepository;
import com.selflearning.service.OrderService;
import com.selflearning.service.SagaOrchestrator;
import com.selflearning.util.IdGeneratorUtil;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;
    private final OrderRepository orderRepository;
    private final SagaOrchestrator orchestrator;

    @Override
    @Transactional
    public void createOrder(CreateOrderRequest request) {
        Order order = mapper.toEntity(request);
        order.setId(IdGeneratorUtil.orderId());
        order.setStatus(OrderStatus.NEW.toString());
        Order savedOrder = orderRepository.save(order);
        OrderCreatedEvent event = new OrderCreatedEvent(
                String.valueOf(savedOrder.getId()),
                savedOrder.getSymbol(),
                BigDecimal.valueOf(savedOrder.getQuantity()),
                savedOrder.getPrice(),
                savedOrder.getSide(),
                savedOrder.getCreatedAt() == null ? LocalDate.now().toString() : savedOrder.getCreatedAt().toString()
        );
        orchestrator.handleOrderCreated(event);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersBySymbol(String symbol) {
        return orderRepository.findBySymbol(symbol);
    }
}
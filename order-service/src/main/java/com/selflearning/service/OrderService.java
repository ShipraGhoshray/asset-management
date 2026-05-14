package com.selflearning.service;

import com.selflearning.messaging.events.OrderCreatedEvent;
import com.selflearning.model.Order;
import com.selflearning.domain.OrderStatus;
import com.selflearning.dto.CreateOrderRequest;
import com.selflearning.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final SagaOrchestrator orchestrator;

    @Transactional
    public void createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setSymbol(request.symbol());
        order.setQuantity(Integer.parseInt(String.valueOf(request.quantity())));
        order.setSide(request.side());
        order.setPrice(request.price());
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
}
package com.selflearning.service;

import com.selflearning.domain.OrderStatus;
import com.selflearning.model.Order;
import com.selflearning.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderStateUpdater {
    private final OrderRepository orderRepository;

    @Transactional
    public void markOrderRejected(String orderId, String reason) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow();
        order.setStatus(OrderStatus.REJECTED.toString());
        orderRepository.save(order);
    }

    @Transactional
    public void markOrderFilled(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow();
        order.setStatus(OrderStatus.FILLED.toString());
        orderRepository.save(order);
    }

    public void markOrderEventReported(String s) {

    }
    public void markExecutionEventReported(String s) {

    }
}

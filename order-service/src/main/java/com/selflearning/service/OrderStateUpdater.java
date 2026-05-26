package com.selflearning.service;

import com.selflearning.domain.OrderStatus;
import com.selflearning.messaging.ExecutionType;
import com.selflearning.messaging.events.TradeExecutionEvent;
import com.selflearning.model.Order;
import com.selflearning.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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

    public Order updateOrderAfterExecution(TradeExecutionEvent tradeEvent){
        Order order = orderRepository.findById(tradeEvent.orderId())
                .orElseThrow(() -> new IllegalStateException("Order not found: " + tradeEvent.orderId()));

        BigDecimal oldFilled = order.getFilledQuantity() == null
                ? BigDecimal.ZERO
                : order.getFilledQuantity();
        BigDecimal newFilled = oldFilled.add(tradeEvent.executedQuantity());
        order.setFilledQuantity(newFilled);

        // simple avg price recompute (you can plug your math util here)
        BigDecimal oldCost = oldFilled.multiply(order.getAvgPrice() == null
                ? BigDecimal.ZERO
                : order.getAvgPrice());
        BigDecimal newCost = oldCost.add(tradeEvent.executedQuantity().multiply(tradeEvent.executedPrice()));
        BigDecimal newAvg = newCost.divide(newFilled, 8, java.math.RoundingMode.HALF_UP);
        order.setAvgPrice(newAvg);

        ExecutionType executionType = newFilled.compareTo(order.getQuantity()) < 0
                ? ExecutionType.PARTIAL_FILL : ExecutionType.FULL_FILL;
        order.setStatus(executionType == ExecutionType.FULL_FILL ? "FILLED" : "PARTIAL_FILLED");
        return orderRepository.save(order);
    }
}

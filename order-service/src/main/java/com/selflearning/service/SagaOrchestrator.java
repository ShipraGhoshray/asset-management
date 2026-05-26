package com.selflearning.service;

import com.selflearning.config.KafkaTopicProperties;
import com.selflearning.messaging.EventType;
import com.selflearning.messaging.ExecutionType;
import com.selflearning.messaging.events.*;
import com.selflearning.messaging.producer.SagaEventPublisher;
import com.selflearning.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SagaOrchestrator {
    private final SagaEventPublisher publisher;
    private final OrderStateUpdater orderStateUpdater;
    private final KafkaTopicProperties topicProps;

    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Saga: Order created, sending CAT report for orderId={}", event.orderId());
        publishReportCommand(event.orderId(), null, EventType.ORDER_CREATED_EVENT);
    }

    public void handleTradeExecution(TradeExecutionEvent event) {
        try {
            log.info("Saga: Trade executed, marking order filled and sending CAT report for execution");
            Order order = orderStateUpdater.updateOrderAfterExecution(event);
            publishOrderExecution(order, event.tradeId(), event.executedQuantity(), event.executedPrice(),
                    order.getStatus().equals("FILLED") ? ExecutionType.FULL_FILL : ExecutionType.PARTIAL_FILL);
            publishReportCommand(event.orderId(), event.tradeId(), EventType.TRADE_EXECUTED_EVENT);
        } catch (Exception ex) {
            log.info("Saga: Trade failed for orderId={}, reason={}", event.orderId(), ex.getMessage());
            orderStateUpdater.markOrderRejected(event.orderId(), ex.getMessage());
        }
    }

    private void publishReportCommand(String orderId, String tradeId, EventType eventType) {
        ReportCommand cmd = new ReportCommand(orderId, tradeId, eventType);
        publisher.send(topicProps.getTopics().getReportCommand(), orderId, cmd);
    }

    public void handleReportEvent(ReportEvent event) {
        log.info("Saga: CAT report succeeded for orderId={}, tradeId={}", event.orderId(), event.tradeId());
        if (event.tradeId() == null) {
            orderStateUpdater.markOrderEventReported(event.orderId());
        } else {
            orderStateUpdater.markExecutionEventReported(event.orderId());
        }
    }

    public void publishOrderExecution(Order order, String tradeId, BigDecimal executedQuantity,
                                 BigDecimal executedPrice, ExecutionType executionType) {
        OrderExecutionEvent event = new OrderExecutionEvent(
                order.getId(),
                tradeId,
                order.getSymbol(),
                executedQuantity,
                executedPrice,
                order.getAccountId(),
                LocalDateTime.now(),
                executionType
        );
        publisher.send("order-execution", event.orderId(), event);
    }
}
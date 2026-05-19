package com.selflearning.service;

import com.selflearning.config.KafkaTopicProperties;
import com.selflearning.messaging.EventType;
import com.selflearning.messaging.events.OrderCreatedEvent;
import com.selflearning.messaging.events.ReportCommand;
import com.selflearning.messaging.events.ReportEvent;
import com.selflearning.messaging.events.TradeExecutionEvent;
import com.selflearning.messaging.producer.SagaEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SagaOrchestrator {
    private final SagaEventPublisher publisher;
    private final OrderStateUpdater orderStateUpdater;
    private final KafkaTopicProperties topicProps;

    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Saga: Order created, sending CAT report for orderId={}", event.orderId());
        ReportCommand cmd = new ReportCommand(event.orderId(), null, EventType.ORDER_CREATED_EVENT);
        publisher.send(
                topicProps.getTopics().getReportCommand(),
                event.orderId(),
                cmd);
    }

    public void handleTradeExecution(TradeExecutionEvent event) {
        try {
            log.info("Saga: Trade executed, marking order filled and sending CAT report for execution");
            orderStateUpdater.markOrderFilled(event.orderId());
            ReportCommand cmd = new ReportCommand(event.orderId(), event.tradeId(), EventType.TRADE_EXECUTED_EVENT);
            publisher.send(
                    topicProps.getTopics().getReportCommand(),
                    event.orderId(),
                    cmd
            );
        } catch (Exception ex) {
            log.info("Saga: Trade failed for orderId={}, reason={}", event.orderId(), ex.getMessage());
            orderStateUpdater.markOrderRejected(event.orderId(), ex.getMessage());
        }
    }

    public void handleReportEvent(ReportEvent event) {
        log.info("Saga: CAT report succeeded for orderId={}, tradeId={}", event.orderId(), event.tradeId());
        if (event.tradeId() == null) {
            orderStateUpdater.markOrderEventReported(event.orderId());
        } else {
            orderStateUpdater.markExecutionEventReported(event.orderId());
        }
    }
}

package com.selflearning.service;

import com.selflearning.config.KafkaTopicProperties;
import com.selflearning.messaging.events.*;
import com.selflearning.messaging.events.ReportCommand;
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
        publisher.send(
                //topicProps.getTopics().getReportCommand(),
                topicProps.getTopics().getOrderCreated(),
                event.orderId(),
                event
        );
    }
    public void handleTradeSuccess(ReportEvent event) {
        log.info("Saga: Trade executed, marking order filled and sending CAT report for execution");
        orderStateUpdater.markOrderFilled(event.orderId());
        ReportCommand cmd = new ReportCommand(event.orderId(), event.tradeId(), "TRADE_EXECUTED");
        publisher.send(
                topicProps.getTopics().getOrderCreated(),
                event.orderId(),
                cmd
        );
    }

    public void handleTradeFailure(ReportEvent event) {
        log.info("Saga: Trade failed for orderId={}, reason={}", event.orderId(), event.reason());
        orderStateUpdater.markOrderRejected(event.orderId(), event.reason());
    }

    public void handleReportEvent(ReportEvent event) {
        log.info("Saga: CAT report succeeded for orderId={}, tradeId={}", event.orderId(), event.tradeId());
        if (event.tradeId() == null) {
            orderStateUpdater.markOrderCatReported(event.orderId());
        } else {
            orderStateUpdater.markExecutionCatReported(event.orderId());
        }
    }
}

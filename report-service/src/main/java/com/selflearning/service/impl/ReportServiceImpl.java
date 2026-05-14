package com.selflearning.service.impl;

import com.selflearning.config.KafkaTopicProperties;
import com.selflearning.dto.ReportRequest;
import com.selflearning.messaging.EventType;
import com.selflearning.messaging.ReportEventPublisher;
import com.selflearning.messaging.ReportStatus;
import com.selflearning.messaging.events.OrderCreatedEvent;
import com.selflearning.messaging.events.ReportEvent;
import com.selflearning.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopicProperties topicProps;
    private final ReportEventPublisher publisher;

    @Override
    public void report(OrderCreatedEvent event) {
        log.info("Starting CAT reporting for orderId={}", event.orderId());
        try {
            // Simulated CAT reporting logic
            ReportEvent successEvent = new ReportEvent(
                    event.orderId(),
                    null,
                    EventType.ORDER_CREATED_EVENT,
                    ReportStatus.SUCCESS,
                    null
            );
            publisher.publish(successEvent);
            log.info("CAT reporting SUCCESS for orderId={}", event.orderId());
        }catch (Exception ex){
            log.error("CAT reporting FAILED for orderId={}", event.orderId(), ex);

            ReportEvent failedEvent = new ReportEvent(
                    event.orderId(),
                    null,
                    EventType.ORDER_CREATED_EVENT,
                    ReportStatus.FAILURE,
                    ex.getMessage()
            );
            publisher.publish(failedEvent);
        }
    }

    @Override
    public void reportCat(ReportRequest request) {
        try {
            log.info("CAT Reporting started for orderId={}, tradeId={}",
                    request.orderId(), request.tradeId());
            // Simulate CAT reporting logic
            Thread.sleep(500);

            ReportEvent successEvent = new ReportEvent(
                    request.orderId(),
                    null,
                    EventType.TRADE_EXECUTED_EVENT,
                    ReportStatus.SUCCESS,
                    null
            );
            kafkaTemplate.send(topicProps.getTopics().getReportEvents(), request.orderId(), request);
            log.info("CAT Reporting SUCCESS for orderId={}", request.orderId());

        } catch (Exception ex) {
            log.error("CAT Reporting FAILED for orderId={}", request.orderId(), ex);
            ReportEvent failedEvent = new ReportEvent(
                    request.orderId(),
                    null,
                    EventType.TRADE_EXECUTED_EVENT,
                    ReportStatus.FAILURE,
                    ex.getMessage()
            );
            kafkaTemplate.send(topicProps.getTopics().getReportEvents(), request.orderId(), request);
        }
    }
}
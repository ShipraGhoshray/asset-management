package com.selflearning.service.impl;

import com.selflearning.config.KafkaTopicProperties;
import com.selflearning.messaging.EventType;
import com.selflearning.messaging.ReportEventPublisher;
import com.selflearning.messaging.ReportStatus;
import com.selflearning.messaging.events.ReportCommand;
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
    public void report(ReportCommand command) {
        try {
            log.info("CAT Reporting started for orderId={}, tradeId={}",
                    command.orderId(), command.tradeId());
            // Simulate CAT reporting logic
            Thread.sleep(500);
            ReportEvent successEvent = getReportEvent(command);
            publisher.publish(successEvent);
            log.info("CAT Reporting SUCCESS for orderId={}", successEvent.orderId());

        } catch (Exception ex) {
            log.error("CAT Reporting FAILED for orderId={}", command.orderId(), ex);
            ReportEvent failedEvent = new ReportEvent(
                    command.orderId(),
                    null,
                    EventType.TRADE_EXECUTED_EVENT,
                    ReportStatus.FAILURE,
                    ex.getMessage()
            );
            kafkaTemplate.send(topicProps.getTopics().getReportEvents(), command.orderId(), command);
        }
    }

    private ReportEvent getReportEvent(ReportCommand command) {
        ReportEvent reportEvent = null;
        if (command.eventType().equals(EventType.ORDER_CREATED_EVENT)){
            reportEvent = new ReportEvent(
                    command.orderId(),
                    null,
                    EventType.ORDER_CREATED_EVENT,
                    ReportStatus.SUCCESS,
                    null
            );
        } else if (command.eventType().equals(EventType.TRADE_EXECUTED_EVENT)) {
            reportEvent = new ReportEvent(
                    command.orderId(),
                    command.tradeId(),
                    EventType.TRADE_EXECUTED_EVENT,
                    ReportStatus.SUCCESS,
                    null
            );
        }
        return reportEvent;
    }
}
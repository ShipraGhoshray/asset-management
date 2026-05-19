package com.selflearning.messaging.consumer;

import com.selflearning.messaging.events.ReportEvent;
import com.selflearning.messaging.events.TradeExecutionEvent;
import com.selflearning.service.SagaOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SagaEventListener {

    private final SagaOrchestrator orchestrator;

    @KafkaListener(
            topics = "${kafka.topics.report-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onReportEvent(ReportEvent event) {
        orchestrator.handleReportEvent(event);
    }

    @KafkaListener(
            topics = "${kafka.topics.trade-execution}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onTradeExecution(TradeExecutionEvent event) {
        orchestrator.handleTradeExecution(event);
    }
}
package com.selflearning.messaging.events;

import com.selflearning.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedEventListener {

    private final ReportService reportService;

    @KafkaListener(
            topics = "${kafka.topics.order-created}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onOrderCreated(OrderCreatedEvent event) {
        log.info("Received Order Created Report Command for orderId={}", event.orderId());
        reportService.report(event);
    }
}
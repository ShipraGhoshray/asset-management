package com.selflearning.messaging;

import com.selflearning.messaging.events.OrderExecutionEvent;
import com.selflearning.messaging.events.TradeExecutionEvent;
import com.selflearning.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderExecutionEventListener {

    private final PortfolioService portfolioService;
    @KafkaListener(
            topics = "${kafka.topics.order-execution}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onOrderExecution(OrderExecutionEvent event) {
        portfolioService.handleOrderExecution(event);
    }
}

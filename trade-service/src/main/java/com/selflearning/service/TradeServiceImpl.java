package com.selflearning.service;

import com.selflearning.client.OrderServiceClient;
import com.selflearning.config.KafkaTopicProperties;
import com.selflearning.dto.ExecutionRequest;
import com.selflearning.dto.OrderStatusResponse;
import com.selflearning.mapper.ExecutionMapper;
import com.selflearning.messaging.OrderStatus;
import com.selflearning.messaging.events.TradeExecutionEvent;
import com.selflearning.messaging.producer.TradeExecutionEventPublisher;
import com.selflearning.model.Executions;
import com.selflearning.repository.TradeRepository;
import com.selflearning.util.IdGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements  TradeService{

    private final ExecutionMapper mapper;
    private final OrderServiceClient orderServiceClient;
    private final TradeRepository repository;
    private final TradeExecutionEventPublisher publisher;
    private final KafkaTopicProperties topicProps;

    @Transactional
    public OrderStatus executeTrade(ExecutionRequest request) {

        OrderStatusResponse status = orderServiceClient.getOrderStatus(request.orderId());
        if (status.status() == OrderStatus.FILLED) {
            return status.status();
        }

        Executions trade = mapper.toEntity(request);
        trade.setId(IdGeneratorUtil.executionId());
        trade.setStatus(OrderStatus.FILLED);
        Executions savedTrade = repository.save(trade);
        TradeExecutionEvent event =
                new TradeExecutionEvent(
                        request.orderId(),
                        savedTrade.getId(),
                        request.symbol(),
                        request.quantity(),
                        request.price()
                );
        publisher.publish(topicProps.getTopics().getTradeExecution(), request.orderId(), event);
        return OrderStatus.EXECUTED;
    }
}